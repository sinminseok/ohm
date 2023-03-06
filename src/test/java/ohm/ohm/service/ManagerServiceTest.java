package ohm.ohm.service;

import ohm.ohm.dto.ManagerDto.ManagerDto;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@Rollback(value = false)
public class ManagerServiceTest {

    @Autowired ManagerService managerService;







}