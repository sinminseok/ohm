package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.PostDto.PostDto;
import ohm.ohm.dto.responseDto.PostResponseDto;
import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Gym.GymImg;
import ohm.ohm.entity.Post.Post;
import ohm.ohm.entity.Post.PostImg;
import ohm.ohm.repository.gym.GymRepository;
import ohm.ohm.repository.post.PostImgRepository;
import ohm.ohm.repository.post.PostRepository;
import ohm.ohm.s3.AmazonS3ResourceStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final PostRepository postRepository;
    private final GymRepository gymRepository;
    private final PostImgRepository postImgRepository;
    private final AppConfig appConfig;

    @Transactional
    public void delete_imgs(List<Long> ids) throws Exception {

        for (Long id : ids) {
            PostImg postImg = postImgRepository.findById(id).get();
            amazonS3ResourceStorage.deleteObjectByKey(postImg.getFilePath());
            postImgRepository.delete(postImg);
        }
    }

    //글 등록 - manager,trainer가 사용
    @Transactional
    public Long save_content(Long gymId, PostDto postDto) throws Exception {
        Optional<Gym> gym = gymRepository.findById(gymId);
        Post post = Post.builder()

                .title(postDto.getTitle())
                .content(postDto.getContent())
                .gym(gym.get())
                .build();

        Post save = postRepository.save(post);
        return save.getId();
    }


    @Transactional
    public Long save_img(Long postId, List<MultipartFile> files) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if (files == null) {

        } else {

            for (MultipartFile multipartFile : files) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter =
                        DateTimeFormatter.ofPattern("yyyyMMdd");
                String current_date = now.format(dateTimeFormatter);
                String uuid_string = UUID.randomUUID().toString();


                String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                //url,orignName

                // 파일 DTO 생성
                PostImg postImg = PostImg.builder()
                        .post(post.get())
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(current_date + File.separator + uuid_string + ext)
                        .build();

                amazonS3ResourceStorage.upload(multipartFile, current_date, uuid_string + ext);
                postImgRepository.save(postImg);
            }

        }
//        List<PostImg> postImgs = fileHandler.postimg_parseFileInfo(post.get(), files);
//
//        if(!postImgs.isEmpty()){
//            for(PostImg postImg :postImgs){
//                PostImg save = postImgRepository.save(postImg);
//
//            }
//        }

        return post.get().getId();

    }


    //헬스장 id로 모든 post조회
    public List<PostResponseDto> findall(Long gymid) {
        List<Post> by_gymId = postRepository.findBy_gymId(gymid);
        List<PostResponseDto> postDtos = new ArrayList<PostResponseDto>();
        for (Post element : by_gymId) {
            postDtos.add(appConfig.modelMapper().map(element, PostResponseDto.class));
        }
        return postDtos;
    }

    //post id로 조회
    public PostResponseDto findById(Long id) {
        Optional<Post> byId = postRepository.findById(id);
        PostResponseDto postDto = appConfig.modelMapper().map(byId.get(), PostResponseDto.class);
        return postDto;
    }

    //변경감지 게시물 수정 (클라이언트에서 수정된 사항은 해당 객체에 업데이트해서 넣고 아닌 값은 원래 객체 값을 대입해서 넣어주자)
    @Transactional
    public Optional<Post> update_post(PostDto postDto) {
        Optional<Post> byId = postRepository.findById(postDto.getId());
        byId.get().update(appConfig.modelMapper().map(postDto, Post.class));
        return byId;
    }

    @Transactional
    public void delete(Long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        postRepository.delete(byId.get());
    }


}
