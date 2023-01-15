package ohm.ohm.api;

import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.LoginDto;
import ohm.ohm.dto.ManagerDto;
import ohm.ohm.dto.TokenDto;
import ohm.ohm.entity.Manager;
import ohm.ohm.jwt.JwtFilter;
import ohm.ohm.jwt.TokenProvider;
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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagerApiController {

    private final ManagerService managerService;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //Manager 인증 API Login
    @PostMapping("/manager/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto){

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getName(),loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer"+jwt);

        return new ResponseEntity<>(new TokenDto(jwt),httpHeaders, HttpStatus.OK);
    }

    //CREATE
    @PostMapping("/manager/signup")
    public ResponseEntity<ManagerDto> signup(@Valid @RequestBody ManagerDto managerDto){
        return ResponseEntity.ok(managerService.signup(managerDto));
    }


    //READ
    @GetMapping("/manager")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ManagerDto> getManagerInfo(){
        return ResponseEntity.ok(managerService.getMyManagerWithAuthorities());
    }



}
