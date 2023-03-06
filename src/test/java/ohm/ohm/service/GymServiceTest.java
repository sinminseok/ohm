package ohm.ohm.service;

import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto.GymDto;
import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.repository.gym.GymRepository;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@Rollback(value = false)
public class GymServiceTest {

    @Autowired GymService gymService;

    @Autowired
    GymRepository gymRepository;

    @Autowired
    AppConfig appConfig;








//    @Test
//    public void currentcount_test() throws Exception{
//        GymDto gymDto = new GymDto("HIGYM",10,5);
//        Gym map = appConfig.modelMapper().map(gymDto, Gym.class);
//        Gym save = gymRepository.save(map);
//
//        int i = gymService.current_count(save.getId());
//        Assertions.assertThat(i).isEqualTo(5);
//    }




}