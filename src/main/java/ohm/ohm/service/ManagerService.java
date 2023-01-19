package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.ManagerDto;
import ohm.ohm.entity.Authority;
import ohm.ohm.entity.Gym;
import ohm.ohm.entity.Manager;
import ohm.ohm.jwt.TokenProvider;
import ohm.ohm.repository.GymRepository;
import ohm.ohm.repository.ManagerRepository;
import ohm.ohm.utils.SecurityUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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


    //JWT
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public List<Manager> findTrainer_byGymId(Long gymId){
        Optional<Gym> byId = gymRepository.findById(gymId);
        List<Manager> managers = byId.get().getManagers();
        return managers;
    }

    //메서드 이름은 추후 signup --> save로 변경
    @Transactional
    public ManagerDto manager_signup(ManagerDto managerDto) {
        if (managerRepository.findOneWithAuthoritiesByName(managerDto.getName()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 매니저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_MANAGER")
                .build();

        Manager manager = new Manager(managerDto.getName(), passwordEncoder.encode(managerDto.getPassword()), managerDto.getAge(), managerDto.getEmail(), Collections.singleton(authority));
        Manager save_manager = managerRepository.save(manager);
        return appConfig.modelMapper().map(save_manager, ManagerDto.class);

    }

    @Transactional
    public ManagerDto trainer_signup(ManagerDto managerDto) {
        if (managerRepository.findOneWithAuthoritiesByName(managerDto.getName()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 매니저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_TRAINER")
                .build();

        Manager manager = new Manager(managerDto.getName(), passwordEncoder.encode(managerDto.getPassword()), managerDto.getAge(), managerDto.getEmail(), Collections.singleton(authority));
        Manager save_manager = managerRepository.save(manager);
        return appConfig.modelMapper().map(save_manager, ManagerDto.class);

    }

    @Transactional
    public Optional<Manager> getManagerrWithAuthorities(String name){
        return managerRepository.findOneWithAuthoritiesByName(name);
    }

    //현재 시큐리티에 담겨져있는
    @Transactional
    public ManagerDto getMyManagerWithAuthorities() {
        return appConfig.modelMapper().map(SecurityUtils.getCurrentUsername().flatMap(managerRepository::findOneWithAuthoritiesByName).get(), ManagerDto.class);
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
    public void remove(Long id) {
        Optional<Manager> byId = managerRepository.findById(id);
        managerRepository.delete(byId.get());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return managerRepository.findOneWithAuthoritiesByName(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + "DB에서 찾을수 없다."));
    }


    private User createUser(String username, Manager manager) {
        List<GrantedAuthority> grantedAuthorities = manager.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new User(manager.getName(), manager.getPassword(), grantedAuthorities);
    }
}
