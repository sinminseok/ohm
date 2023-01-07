package ohm.ohm.service;

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

import java.util.Optional;

import static org.junit.Assert.*;



@SpringBootTest
@Transactional
@Rollback(value = false)
@RunWith(SpringRunner.class)
public class PostServiceTest {

    @Autowired
    PostService postService;

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

    @Test
    public void update_test(){
        PostDto postDto = new PostDto("titlde","contenttt");
        Long save = postService.save(postDto);
        PostDto updateDto = new PostDto(save,"changetitle","contenttt");
        Optional<Post> update = postService.update(updateDto);

        Assertions.assertThat(update.get().getTitle()).isEqualTo("changetitle");
    }
}