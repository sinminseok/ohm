package ohm.ohm.service;

import ohm.ohm.dto.AdminDto;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
public class AdminServiceTest {


    @Autowired AdminService adminService;

//    @Test
//    public void save_admin(){
//        AdminDto adminDto = new AdminDto("testddadmin");
//        Long join = adminService.join(adminDto);
//        assertThat(join).isNotNull();
//    }




}