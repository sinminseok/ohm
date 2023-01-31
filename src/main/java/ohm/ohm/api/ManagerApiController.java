package ohm.ohm.api;

import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.LoginDto;
import ohm.ohm.dto.ManagerDto;
import ohm.ohm.dto.TokenDto;
import ohm.ohm.dto.responseDto.TrainerResponseDto;
import ohm.ohm.entity.Manager;
import ohm.ohm.jwt.JwtFilter;
import ohm.ohm.jwt.TokenProvider;
import ohm.ohm.service.GymService;
import ohm.ohm.service.ManagerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagerApiController {

    private final ManagerService managerService;
    private final TokenProvider tokenProvider;
    private final GymService gymService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //Manager 인증 API Login
    @PostMapping("/manager/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer" + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    //manager 계정 생성
    @PostMapping("/manager")
    public ResponseEntity<ManagerDto> manager_signup(@Valid @RequestBody ManagerDto managerDto) {
        return ResponseEntity.ok(managerService.manager_save(managerDto));
    }

    //manager_trainer 계정생성
    @PostMapping("/trainer")
    public ResponseEntity<ManagerDto> trainer_signup(@Valid @RequestBody ManagerDto managerDto) {
        return ResponseEntity.ok(managerService.trainer_save(managerDto));
    }


    //현재 로그인된 Manager 정보조회
    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER')")
    public ResponseEntity<ManagerDto> getManagerInfo() {
        return ResponseEntity.ok(managerService.getMyManagerWithAuthorities());
    }


    @GetMapping("/manager/{managerId}")
    public ResponseEntity<TrainerResponseDto> trauber_find(
            @PathVariable Long managerId
    ){
        TrainerResponseDto byID = managerService.findByID(managerId);
        return ResponseEntity.ok(byID);
    }


    @GetMapping("/manager/findall/{gymId}")
    public ResponseEntity<List<TrainerResponseDto>> trainer_findall(
            @PathVariable Long gymId
    ) {
        List<TrainerResponseDto> trainerResponseDtos = managerService.trainer_findall(gymId);
        return ResponseEntity.ok(trainerResponseDtos);
    }


}
