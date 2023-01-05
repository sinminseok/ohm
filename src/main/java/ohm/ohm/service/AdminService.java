package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.AdminDto;
import ohm.ohm.dto.GymDto;
import ohm.ohm.entity.Admin;
import ohm.ohm.entity.Gym;
import ohm.ohm.repository.AdminRepository;
import ohm.ohm.repository.GymRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;




@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final GymRepository gymRepository;
    private final AppConfig appConfig;

    //Admin 계정 생성 service
    //추후 Spring security로 로그인 로직 구현
    @Transactional
    public Long join(AdminDto adminDto){
        Admin admin = appConfig.modelMapper().map(adminDto, Admin.class);
        Long id = adminRepository.save(admin).getId();
        return id;
    }

    //제휴중인 모든 헬스장조회
//    public List<GymDto> findall_Gym() {
//        List<Gym> gyms = gymRepository.findAll();
//        List<GymDto> gymDtos = new ArrayList<GymDto>();
//        for (Gym gym : gyms) {
//            gymDtos.add(appConfig.modelMapper().map(gym,GymDto.class));
//        }
//    }


}
