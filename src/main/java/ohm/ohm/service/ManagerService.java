package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto.GymDto;
import ohm.ohm.dto.ManagerDto.ManagerDto;
import ohm.ohm.dto.requestDto.ManagerRequestDto;
import ohm.ohm.dto.responseDto.TrainerResponseDto;
import ohm.ohm.entity.Manager.Authority;
import ohm.ohm.entity.Manager.Manager;
import ohm.ohm.repository.GymRepository;
import ohm.ohm.repository.ManagerRepository;
import ohm.ohm.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService implements UserDetailsService {

    private final ManagerRepository managerRepository;
    private final GymRepository gymRepository;
    private final AppConfig appConfig;
    private final PasswordEncoder passwordEncoder;


    //Manager 회원가입
    @Transactional
    public ManagerDto manager_save(ManagerRequestDto managerDto) {
        if (managerRepository.findOneWithAuthoritiesByName(managerDto.getName()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 매니저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_MANAGER")
                .build();


        Manager manager = Manager.builder()
                .name(managerDto.getName())
                .password(passwordEncoder.encode(managerDto.getPassword()))
                .nickname(managerDto.getNickname())
                .age(managerDto.getAge())
                .email(managerDto.getEmail())
                .profile(managerDto.getProfile())
                .oneline_introduce(managerDto.getOneline_introduce())
                .introduce(managerDto.getIntroduce())
                .authorities(Collections.singleton(authority))
                .build();


        Manager save_manager = managerRepository.save(manager);
        return appConfig.modelMapper().map(save_manager, ManagerDto.class);

    }

    //Trainer 회원가입
    @Transactional
    public ManagerDto trainer_save(ManagerRequestDto managerDto) {
        if (managerRepository.findOneWithAuthoritiesByName(managerDto.getName()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 매니저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_TRAINER")
                .build();

        Manager manager = new Manager(managerDto.getName(), passwordEncoder.encode(managerDto.getPassword()),managerDto.getNickname(),managerDto.getProfile(),managerDto.getOneline_introduce(),managerDto.getIntroduce() , managerDto.getAge(),managerDto.getEmail(), Collections.singleton(authority));
        Manager save_manager = managerRepository.save(manager);
        return appConfig.modelMapper().map(save_manager, ManagerDto.class);

    }


    //현재 시큐리티에 담겨져있는 계정 권한 가져오는 메서드
    @Transactional
    public ManagerDto getMyManagerWithAuthorities() {
        return appConfig.modelMapper().map(SecurityUtils.getCurrentUsername().flatMap(managerRepository::findOneWithAuthoritiesByName).get(), ManagerDto.class);
    }
    
    @Transactional
    public ManagerDto getManagerInfo(Long id){

//        Manager findmanager = managerRepository.findManagerFetchJoinGym(id);
        Optional<Manager> findmanager = managerRepository.findOneWithGymById(id);
        System.out.println("findmanagerfindmanager =" +findmanager.get().getGym().getName());
        ManagerDto managerDto = ManagerDto.builder()
                .name(findmanager.get().getName())
                .age(findmanager.get().getAge())
                .gymDto(appConfig.modelMapper().map(findmanager.get().getGym(), GymDto.class))
                .id(findmanager.get().getId())
//                .authorities(findmanager.getAuthorities())
                .email(findmanager.get().getEmail())
                .nickname(findmanager.get().getNickname())
                .introduce(findmanager.get().getIntroduce())
                .oneline_introduce(findmanager.get().getOneline_introduce())
                .profile(findmanager.get().getProfile())
                .build();
//        System.out.println("findmanagerfindmanager =" +findmanager.getGym().getName());
//        ManagerDto managerDto = ManagerDto.builder()
//                .name(findmanager.getName())
//                .age(findmanager.getAge())
//                .gymDto(appConfig.modelMapper().map(findmanager.getGym(), GymDto.class))
//                .id(findmanager.getId())
////                .authorities(findmanager.getAuthorities())
//                .email(findmanager.getEmail())
//                .nickname(findmanager.getNickname())
//                .introduce(findmanager.getIntroduce())
//                .oneline_introduce(findmanager.getOneline_introduce())
//                .profile(findmanager.getProfile())
//                .build();

        return managerDto;
    }


    //Id로 매니저 조회
    public TrainerResponseDto findByID(Long id) {
        Optional<Manager> byId = managerRepository.findById(id);
        return appConfig.modelMapper().map(byId.get(), TrainerResponseDto.class);
    }

    public List<TrainerResponseDto> trainer_findall(Long gymId){
        List<Optional<Manager>> managers = managerRepository.findall_byGymId(gymId);
        List<TrainerResponseDto> trainerResponseDtos = new ArrayList<TrainerResponseDto>();

        for(Optional<Manager> manager : managers){
            trainerResponseDtos.add(appConfig.modelMapper().map(manager.get(),TrainerResponseDto.class));
        }

        return trainerResponseDtos;
    }


    //매니저 정보수정
    public Optional<Manager> update(ManagerDto updateDto) {
        //업데이트될 정보
        Manager map = appConfig.modelMapper().map(updateDto, Manager.class);

        //기본 manager 조회
        Optional<Manager> byId = managerRepository.findById(updateDto.getId());

      //update생성자로 변경감지
        byId.get().update(map);
        return byId;
    }


    //Gym을 save할때 manager와 연관관계를 맺어주는 메서드
    @Transactional
    public void register_gym(Long gymId,Long manager_id){
        managerRepository.registerByGymId(manager_id,gymId);

    }


    //매니저 삭제
    public void remove(Long id) {
        Optional<Manager> byId = managerRepository.findById(id);
        managerRepository.delete(byId.get());
    }

   // ------------시큐리티에서 사용되는 메서드 --------------
    private User createUser(String username, Manager manager) {
        List<GrantedAuthority> grantedAuthorities = manager.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new User(manager.getName(), manager.getPassword(), grantedAuthorities);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return managerRepository.findOneWithAuthoritiesByName(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + "DB에서 찾을수 없다."));
    }

}
