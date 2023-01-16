package ohm.ohm.api;


import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.LoginDto;
import ohm.ohm.dto.TokenDto;
import ohm.ohm.dto.TrainerDto;
import ohm.ohm.jwt.JwtFilter;
import ohm.ohm.jwt.TokenProvider;
import ohm.ohm.service.TrainerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TrainerApiController {

    private final TrainerService trainerService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    //Trainer 로그인
    @PostMapping("/trainer/login")
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

    @PostMapping("/trainer/signup")
    public ResponseEntity<TrainerDto> signup(@Valid @RequestBody TrainerDto trainerDto){
        return
    }




}
