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

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final GymRepository gymRepository;
    private final AppConfig appConfig;


    //매니저(헬스장 관리자) - 추후 부여된 가입코드로 가입
    @Transactional
    public Long save(ManagerDto managerDto) {
        Manager manager = appConfig.modelMapper().map(managerDto, Manager.class);
        return managerRepository.save(manager).getId();
    }


    //Id로 매니저 조회
    public ManagerDto findByID(Long id) {
        Optional<Manager> byId = managerRepository.findById(id);
        return appConfig.modelMapper().map(byId.get(), ManagerDto.class);
    }


    //매니저 정보수정
    public Optional<Manager> update(ManagerDto updateDto) {
        Manager map = appConfig.modelMapper().map(updateDto, Manager.class);
        Optional<Manager> byId = managerRepository.findById(updateDto.getId());
        byId.get().update(map);
        return byId;
    }


    //매니저 삭제
    public void remove(Long id){
        Optional<Manager> byId = managerRepository.findById(id);
        managerRepository.delete(byId.get());
    }


}
