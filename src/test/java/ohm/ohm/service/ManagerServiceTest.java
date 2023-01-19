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

import java.util.Optional;

import static org.junit.Assert.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
@RunWith(SpringRunner.class)
public class ManagerServiceTest {

    @Autowired ManagerService managerService;

    @Test
    public void save_test(){
        ManagerDto managerDto = new ManagerDto("admin","admin");
        ManagerDto managerDto1 = managerService.manager_signup(managerDto);
        Assertions.assertThat(managerDto1).isNotNull();
    }

    @Test
    public void findById_test(){
        ManagerDto managerDto = new ManagerDto("name","email");
        ManagerDto managerDto1 = managerService.manager_signup(managerDto);
        ManagerDto byID = managerService.findByID(managerDto1.getId());
        Assertions.assertThat(byID.getName()).isEqualTo("name");
    }

    @Test
    public void update_test(){
        ManagerDto managerDto = new ManagerDto("name","email");
        ManagerDto managerDto1 = managerService.manager_signup(managerDto);
        ManagerDto updateDto = new ManagerDto(managerDto1.getId(),"change_name","change_email");
        Optional<Manager> update = managerService.update(updateDto);
        Assertions.assertThat(update.get().getName()).isEqualTo("change_name");
    }

    @Test
    public void testt(){
        managerService.findTrainer_byGymId(2L);
    }





}