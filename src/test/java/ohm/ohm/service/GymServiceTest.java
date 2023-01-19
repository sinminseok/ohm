package ohm.ohm.service;

import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto;
import ohm.ohm.entity.Gym;
import ohm.ohm.repository.GymRepository;
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


@SpringBootTest
@Transactional
@Rollback(value = false)
@RunWith(SpringRunner.class)
public class GymServiceTest {

    @Autowired GymService gymService;

    @Autowired
    GymRepository gymRepository;

    @Autowired
    AppConfig appConfig;


    @Test
    public void save_test(){
        GymDto gymDto = new GymDto("testGym",10,5);
        gymService.save(gymDto);
    }

    @Test
    public void findByName_test(){
        GymDto gymDto = new GymDto("HIGYM",10,5);
        gymService.save(gymDto);
        List<GymDto> hi = gymService.findByName("GYM");
        Assertions.assertThat(hi.get(0).getName()).isEqualTo("HIGYM");
    }


    @Test
    public void findById_test() throws Exception {
        GymDto gymDto = new GymDto("TEST",10,5);
        Long save = gymService.save(gymDto);

        GymDto byId = gymService.findById(2L);
        System.out.println("byIdbyId ="+byId.getId());

    }

    @Test
    public void currentcount_test() throws Exception{
        GymDto gymDto = new GymDto("HIGYM",10,5);
        Gym map = appConfig.modelMapper().map(gymDto, Gym.class);
        Gym save = gymRepository.save(map);

        int i = gymService.current_count(save.getId());
        Assertions.assertThat(i).isEqualTo(5);
    }

    //헬스장 인원증가
    @Test
    public void increasecount_test() throws Exception {
        GymDto gymDto = new GymDto("HIGYM",10,4);
        Gym map = appConfig.modelMapper().map(gymDto, Gym.class);
        gymRepository.save(map);
        gymService.increase_count(1L);
        gymService.increase_count(1L);
        GymDto byId = gymService.findById(1L);
        Assertions.assertThat(byId.getCurrent_count()).isEqualTo(6);

    }


}