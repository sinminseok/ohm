package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto.GymDto;
import ohm.ohm.dto.ManagerDto.ManagerDto;
import ohm.ohm.dto.requestDto.ManagerRequestDto;
import ohm.ohm.dto.responseDto.TrainerResponseDto;
import ohm.ohm.entity.Code;
import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Manager.Authority;
import ohm.ohm.entity.Manager.Manager;
import ohm.ohm.repository.manager.CodeRepository;
import ohm.ohm.repository.gym.GymRepository;
import ohm.ohm.repository.manager.ManagerRepository;
import ohm.ohm.s3.AmazonS3ResourceStorage;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService implements UserDetailsService {
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final ManagerRepository managerRepository;
    private final GymRepository gymRepository;
    private final AppConfig appConfig;
    private final CodeRepository codeRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean check_code(String code) {
        Optional<Code> code1 = codeRepository.findCode(code);
        if (code1.get() == null) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void profile_save(Long managerId, MultipartFile multipartFile) throws Exception {
        Optional<Manager> byId = managerRepository.findById(managerId);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String current_date = now.format(dateTimeFormatter);
        String uuid_string = UUID.randomUUID().toString();


        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        //url,orignName
        byId.get().register_profile(current_date + File.separator + uuid_string + ext, multipartFile.getOriginalFilename());
        amazonS3ResourceStorage.upload(multipartFile, current_date, uuid_string + ext);
    }

    @Transactional
    public void profile_edit(Long managerId, MultipartFile multipartFile) throws Exception {
        Optional<Manager> byId = managerRepository.findById(managerId);
        String profileUrl = byId.get().getProfileUrl();

        if (profileUrl == null) {

        } else {
            amazonS3ResourceStorage.deleteObjectByKey(byId.get().getProfileUrl());
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String current_date = now.format(dateTimeFormatter);
        String uuid_string = UUID.randomUUID().toString();


        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        //url,orignName
        byId.get().register_profile(current_date + File.separator + uuid_string + ext, multipartFile.getOriginalFilename());
        amazonS3ResourceStorage.upload(multipartFile, current_date, uuid_string + ext);


        byId.get().register_profile(current_date + File.separator + uuid_string + ext, multipartFile.getOriginalFilename());
    }

    //Manager 회원가입
    @Transactional
    public ManagerDto manager_save(ManagerRequestDto managerDto, Long gymId) {
        if (managerRepository.findOneWithAuthoritiesByName(managerDto.getName()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 매니저입니다.");
        }
        Optional<Gym> gym_optional = gymRepository.findById(gymId);
        Authority authority = Authority.builder()
                .authorityName("ROLE_MANAGER")
                .build();

        Manager manager = Manager.builder()
                .position(managerDto.getPosition())
                .name(managerDto.getName())
                .gym(gym_optional.get())
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

    //Manager 회원가입
    @Transactional
    public ManagerDto ceo_save(ManagerRequestDto managerDto) {
        if (managerRepository.findOneWithAuthoritiesByName(managerDto.getName()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 아이디.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_CEO")
                .build();


        Manager manager = Manager.builder()
                .name(managerDto.getName())
                .position(managerDto.getPosition())
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
    public ManagerDto trainer_save(ManagerRequestDto managerDto, Long gymId) {
        if (managerRepository.findOneWithAuthoritiesByName(managerDto.getName()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 트레이너 입니다.");
        }
        Optional<Gym> gym_optional = gymRepository.findById(gymId);

        Authority authority = Authority.builder()
                .authorityName("ROLE_TRAINER")
                .build();

        Manager manager = Manager.builder()
                .name(managerDto.getName())
                .position(managerDto.getPosition())
                .gym(gym_optional.get())
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


    //현재 시큐리티에 담겨져있는 계정 권한 가져오는 메서드
    @Transactional
    public ManagerDto getMyManagerWithAuthorities() {
        return appConfig.modelMapper().map(SecurityUtils.getCurrentUsername().flatMap(managerRepository::findOneWithAuthoritiesByName).get(), ManagerDto.class);
    }

    @Transactional
    public ManagerDto getManagerInfo(Long id) {

//        Manager findmanager = managerRepository.findManagerFetchJoinGym(id);
        Optional<Manager> findmanager = managerRepository.findOneWithGymById(id);

        ManagerDto managerDto = ManagerDto.builder()
                .name(findmanager.get().getName())
                .gymDto(appConfig.modelMapper().map(findmanager.get().getGym(), GymDto.class))
                .id(findmanager.get().getId())
                .nickname(findmanager.get().getNickname())
                .introduce(findmanager.get().getIntroduce())
                .oneline_introduce(findmanager.get().getOneline_introduce())
                .profile(findmanager.get().getProfileUrl())
                .build();


        return managerDto;
    }


    //Id로 매니저 조회
    public TrainerResponseDto findByID(Long id) {
        Optional<Manager> byId = managerRepository.findById(id);
        return appConfig.modelMapper().map(byId.get(), TrainerResponseDto.class);
    }

    public List<TrainerResponseDto> trainer_findall(Long gymId) {
        List<Optional<Manager>> managers = managerRepository.findall_byGymId(gymId);
        List<TrainerResponseDto> trainerResponseDtos = new ArrayList<TrainerResponseDto>();

        for (Optional<Manager> manager : managers) {
            trainerResponseDtos.add(appConfig.modelMapper().map(manager.get(), TrainerResponseDto.class));
        }

        return trainerResponseDtos;
    }


    //매니저 정보수정
    @Transactional
    public Optional<Manager> update(ManagerDto updateDto) {


        Optional<Manager> byId = managerRepository.findById(updateDto.getId());
        //update생성자로 변경감지
        byId.get().update(updateDto);
        return byId;
    }


    //Gym을 save할때 manager와 연관관계를 맺어주는 메서드
    @Transactional
    public void register_gym(Long gymId, Long manager_id) {
        managerRepository.registerByGymId(manager_id, gymId);

    }


    //매니저 삭제
    @Transactional
    public void delete(Long id) {
        Optional<Manager> byId = managerRepository.findById(id);
        managerRepository.delete(byId.get());
    }

    // ------------시큐리티에서 사용되는 메서드 --------------
    @Transactional
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
