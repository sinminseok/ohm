package ohm.ohm.service;

import ohm.ohm.dto.ManagerDto;
import ohm.ohm.entity.Manager;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@Rollback(value = false)
@RunWith(SpringRunner.class)
public class ManagerServiceTest {

    @Autowired ManagerService managerService;

    @Test
    public void save_test(){
        ManagerDto managerDto = new ManagerDto("admin","admin");
        ManagerDto managerDto1 = managerService.manager_save(managerDto);
        Assertions.assertThat(managerDto1).isNotNull();
    }






}