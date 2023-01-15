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
        Long save = managerService.save(managerDto);
        Assertions.assertThat(save).isNotNull();
    }

    @Test
    public void findById_test(){
        ManagerDto managerDto = new ManagerDto("name","email");
        Long save = managerService.save(managerDto);
        ManagerDto byID = managerService.findByID(save);
        Assertions.assertThat(byID.getName()).isEqualTo("name");
    }

    @Test
    public void update_test(){
        ManagerDto managerDto = new ManagerDto("name","email");
        Long save = managerService.save(managerDto);
        ManagerDto updateDto = new ManagerDto(save,"change_name","change_email");
        Optional<Manager> update = managerService.update(updateDto);
        Assertions.assertThat(update.get().getName()).isEqualTo("change_name");
    }





}