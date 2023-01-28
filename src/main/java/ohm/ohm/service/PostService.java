package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto;
import ohm.ohm.dto.PostDto;
import ohm.ohm.entity.Gym;
import ohm.ohm.entity.Post;
import ohm.ohm.repository.GymRepository;
import ohm.ohm.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {


    private final PostRepository postRepository;
    private final AppConfig appConfig;

    //글 등록 - manager,trainer가 사용
    @Transactional
    public Long save(PostDto postDto) {
        Post post = appConfig.modelMapper().map(postDto, Post.class);
        return postRepository.save(post).getId();
    }


    //헬스장 id로 모든 post조회
    public List<PostDto> findall(Long gymid) {
        List<Post> by_gymId = postRepository.findBy_gymId(gymid);
        List<PostDto> postDtos = new ArrayList<PostDto>();
        for(Post element : by_gymId){
            postDtos.add(appConfig.modelMapper().map(element,PostDto.class));
        }
        return postDtos;
    }

    //post id로 조회
    public PostDto findById(Long id) {
        Optional<Post> byId = postRepository.findById(id);
        PostDto postDto = appConfig.modelMapper().map(byId.get(), PostDto.class);
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
