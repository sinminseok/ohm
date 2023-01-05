package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.AdminDto;
import ohm.ohm.dto.ManagerDto;
import ohm.ohm.entity.Admin;
import ohm.ohm.entity.Manager;
import ohm.ohm.repository.AdminRepository;
import ohm.ohm.repository.GymRepository;
import ohm.ohm.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final GymRepository gymRepository;
    private final AppConfig appConfig;


    //Manager register method
    @Transactional
    public void registerManager(ManagerDto managerDto){
        Manager manager = appConfig.modelMapper().map(managerDto, Manager.class);
        managerRepository.save(manager);
    }

    //Admin 계정 생성 service
    @Transactional
    public Long join(ManagerDto managerDto){
        Manager manager = appConfig.modelMapper().map(managerDto, Manager.class);
        return managerRepository.save(manager).getId();
    }


    //
//    public ManagerDto findByName(){
//
//    }



}
