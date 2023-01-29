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









}