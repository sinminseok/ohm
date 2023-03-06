package ohm.ohm.service;

import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Post.Post;
import ohm.ohm.repository.gym.GymRepository;
import ohm.ohm.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
public class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    GymRepository gymRepository;

//    @BeforeAll
    public void setUp() {

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

    }

    @Test
    public void getPostListTest() {
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

        List<Gym> gym = gymRepository.findByNameContaining("비나이더");

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "title"));
        Slice<Post> p = postRepository.findBy_gymId(gym.get(0).getId(), pageRequest);
        List<Post> posts = p.getContent();

        System.out.println(p.getSize());

        for (Post post : posts) {
            System.out.println(post.getTitle());
        }

        assertThat(p.hasNext()).isTrue();
        assertThat(p.hasPrevious()).isFalse();

    }


}