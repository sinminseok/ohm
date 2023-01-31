package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto;
import ohm.ohm.dto.PostDto;
import ohm.ohm.dto.responseDto.PostResponseDto;
import ohm.ohm.entity.Gym;
import ohm.ohm.entity.Post;
import ohm.ohm.entity.PostImg;
import ohm.ohm.repository.GymRepository;
import ohm.ohm.repository.PostImgRepository;
import ohm.ohm.repository.PostRepository;
import ohm.ohm.utils.FileHandlerUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {


    private final PostRepository postRepository;
    private final GymRepository gymRepository;
    private final PostImgRepository postImgRepository;
    private final AppConfig appConfig;
    private final FileHandlerUtils fileHandler;

    //글 등록 - manager,trainer가 사용
    @Transactional
    public Long save(Long gymId,PostDto postDto, List<MultipartFile> files) throws Exception {
        Optional<Gym> gym = gymRepository.findById(gymId);


        Post post = Post.builder()

                .title(postDto.getTitle())
                .content(postDto.getContent())
                .gym(gym.get())
                .build();

        Post save = postRepository.save(post);

        List<PostImg> postImgs = fileHandler.postimg_parseFileInfo(save, files);

        if(!postImgs.isEmpty()){
            for(PostImg postImg :postImgs){
                postImgRepository.save(postImg);
            }
        }

        return save.getId();
    }


    //헬스장 id로 모든 post조회
    public List<PostResponseDto> findall(Long gymid) {
        List<Post> by_gymId = postRepository.findBy_gymId(gymid);
        List<PostResponseDto> postDtos = new ArrayList<PostResponseDto>();
        for(Post element : by_gymId){
            postDtos.add(appConfig.modelMapper().map(element,PostResponseDto.class));
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
    public Optional<Post> update(PostDto updateDto) {
        Post update = appConfig.modelMapper().map(updateDto, Post.class);
        Optional<Post> byId = postRepository.findById(update.getId());
        byId.get().update(update);
        return byId;
    }

}
