package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.AdminDto;
import ohm.ohm.entity.Admin;
import ohm.ohm.repository.manager.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final AppConfig appConfig;


    @Transactional
    public Long save(AdminDto adminDto){
        Admin admin = appConfig.modelMapper().map(adminDto, Admin.class);
        Long id = adminRepository.save(admin).getId();
        return id;
    }



}
