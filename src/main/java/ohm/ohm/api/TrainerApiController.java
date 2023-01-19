//package ohm.ohm.api;
//
//
//import lombok.RequiredArgsConstructor;
//import ohm.ohm.dto.LoginDto;
//import ohm.ohm.dto.TokenDto;
//import ohm.ohm.dto.TrainerDto;
//import ohm.ohm.jwt.JwtFilter;
//import ohm.ohm.jwt.TokenProvider;
//import ohm.ohm.service.TrainerService;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class TrainerApiController {
//
//    private final TrainerService trainerService;
//    private final TokenProvider tokenProvider;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
//
//    //Trainer 로그인
//    @PostMapping("/trainer/login")
//    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto){
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginDto.getName(),loginDto.getPassword());
//
//        System.out.println("FASFASF =="+authenticationToken);
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = tokenProvider.createToken(authentication);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer"+jwt);
//
//        return new ResponseEntity<>(new TokenDto(jwt),httpHeaders, HttpStatus.OK);
//    }
//
//
//    //CREATE
//    @PostMapping("/trainer/signup/{gymId}")
//    public ResponseEntity<TrainerDto> signup(@PathVariable Long gymId , @Valid @RequestBody TrainerDto trainerDto){
//        return ResponseEntity.ok(trainerService.signup(trainerDto,gymId));
//    }
//
//    @GetMapping("/trainer")
//    public ResponseEntity<TrainerDto> getTrainerInfo(){
//        return ResponseEntity.ok(trainerService.getMyManagerWithAuthorities());
//    }
//
//
//    //GymId로 해당 Gym에 소속된 트레이너 모두 조회
//    @GetMapping("/trainer/gym/{gymId}")
//    public ResponseEntity<List<TrainerDto>> findallByGym(@PathVariable Long gymId){
//
//        List<TrainerDto> trainerDtos = trainerService.findall(gymId);
//
//
//        return ResponseEntity.ok(trainerDtos);
//    }
//
//    //트레이너 Id로 트레이너 조회
//    @GetMapping("/trainer/{trainerId}")
//    public ResponseEntity<TrainerDto> findById(@PathVariable Long trainerId){
//        TrainerDto trainerdto = trainerService.findById(trainerId);
//        return ResponseEntity.ok(trainerdto);
//    }
//
//
//
//
//}
