package ohm.ohm.api;

import ohm.ohm.dto.responseDto.PostResponseDto;
import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Post.Post;
import ohm.ohm.repository.gym.GymRepository;
import ohm.ohm.repository.post.PostRepository;
import ohm.ohm.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostApiControllerTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    GymRepository gymRepository;

    @Test
    void sliceTest() {
        Gym gym1 = Gym.builder().name("비나이더").build();

        gymRepository.save(gym1);

        for (var i = 1; i <= 22; i++) {
            Post post = Post.builder()
                    .title("title" + i)
                    .content("contents" + i)
                    .gym(gym1)
                    .build();

            postRepository.save(post);
        }


        Slice<PostResponseDto> postResponseDtos = postService.findall(gym1.getId(), 0);

        System.out.println(postResponseDtos.getContent().get(0).getTitle());

        Assertions.assertThat(postResponseDtos.getSize()).isEqualTo(10);
    }
}
