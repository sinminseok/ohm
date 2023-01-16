package ohm.ohm.service;

import ohm.ohm.dto.GymDto;
import ohm.ohm.dto.PostDto;
import ohm.ohm.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;



@SpringBootTest
@Transactional
@Rollback(value = false)
@RunWith(SpringRunner.class)
public class PostServiceTest {

    @Autowired
    GymService gymService;

    @Autowired
    PostService postService;

    @Autowired


    @Test
    public void save_test(){
        PostDto postDto = new PostDto("titlde","contenttt");
        Long save = postService.save(postDto);
        Assertions.assertThat(save).isNotNull();
    }

    @Test
    public void findById_test(){
        PostDto postDto = new PostDto("titlde","contenttt");
        Long save = postService.save(postDto);
        PostDto byId = postService.findById(save);
        Assertions.assertThat(byId.getTitle()).isEqualTo("titlde");
    }

    //Gym id로 해당 Gym으로 올라온 Post 모두 조회
    @Test
    public void findall() throws Exception {
        GymDto gymDto = new GymDto("test_gym",20,0);
        Long gym_id = gymService.save(gymDto);
        GymDto findgym = gymService.findById(gym_id);

        PostDto postDto1 = new PostDto("title1","contenttt",findgym);
        PostDto postDto2 = new PostDto("title2","contenttt",findgym);
        PostDto postDto3 = new PostDto("title3","contenttt",findgym);
        postService.save(postDto1);
        postService.save(postDto2);
        postService.save(postDto3);


        List<PostDto> findall = postService.findall(gym_id);
        Assertions.assertThat(findall.size()).isEqualTo(3);

    }

    @Test
    public void update_test(){
        PostDto postDto = new PostDto("titlde","contenttt");
        Long save = postService.save(postDto);
        PostDto updateDto = new PostDto(save,"changetitle","contenttt");
        Optional<Post> update = postService.update(updateDto);

        Assertions.assertThat(update.get().getTitle()).isEqualTo("changetitle");
    }
}