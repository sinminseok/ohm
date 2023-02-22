package ohm.ohm.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.ManagerDto.LoginDto;
import ohm.ohm.dto.ManagerDto.ManagerDto;
import ohm.ohm.dto.ManagerDto.TokenDto;
import ohm.ohm.dto.PostDto.PostDto;
import ohm.ohm.dto.requestDto.ManagerRequestDto;
import ohm.ohm.dto.responseDto.TrainerResponseDto;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"Manager API"})
@RequiredArgsConstructor
public class ManagerApiController {

    private final ManagerService managerService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @ApiOperation(value = "manager code 인증", response = String.class)
    @PostMapping("/manager/code/{code}")
    public ResponseEntity<String> check_code(
            @PathVariable String code) {

        boolean bool = managerService.check_code(code);
        if(bool == true){
            return ResponseEntity.ok("true");
        }else{
            return ResponseEntity.ok("false");
        }
    }



    @ApiOperation(value = "manager,trainer 로그인", response = TokenDto.class)
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


    @ApiOperation(value = "manager 회원가입", response = ManagerDto.class)
    @PostMapping("/manager")
    public ResponseEntity<ManagerDto> manager_signup(@Valid @RequestBody ManagerRequestDto managerDto) {
        return ResponseEntity.ok(managerService.manager_save(managerDto));
    }


    @ApiOperation(value = "Manager profile 등록", response = Long.class)
    @PostMapping("/manager/image/{managerId}")
    public ResponseEntity<String> save_img(
            @PathVariable Long managerId,
            @RequestPart(value = "images",required = false) MultipartFile file
    ) throws Exception {
        managerService.profile_save(managerId,file);
        return ResponseEntity.ok("image upload!");
    }

    @ApiOperation(value = "Manager profile 수정", response = Long.class)
    @PatchMapping("/manager/image/{managerId}")
    public ResponseEntity<String> profile_update(
            @PathVariable Long managerId,
            @RequestPart(value = "images",required = false) MultipartFile file
    ) throws Exception {
        managerService.profile_edit(managerId,file);
        return ResponseEntity.ok("image upload!");
    }




    @ApiOperation(value = "trainer 회원가입", response = ManagerDto.class)
    @PostMapping("/trainer/{gymId}")
    public ResponseEntity<ManagerDto> trainer_signup(
            @PathVariable Long gymId,
            @Valid @RequestBody ManagerRequestDto managerDto) {
        return ResponseEntity.ok(managerService.trainer_save(managerDto,gymId));
    }


    //현재 로그인된 Manager 정보조회
    @ApiOperation(value = "로그인된 Manager 정보조회", response = ManagerDto.class)
    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER')")
    public ResponseEntity<ManagerDto> getManagerInfo() {
        return ResponseEntity.ok(managerService.getMyManagerWithAuthorities());
    }


    @ApiOperation(value = "Manager ID로 정보조회", response = ManagerDto.class)
    @GetMapping("/manager/info/{managerId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER')")
    public ResponseEntity<ManagerDto> getManagerInfoById(
            @PathVariable Long managerId

    ) {
        ManagerDto managerInfo = managerService.getManagerInfo(managerId);
        return ResponseEntity.ok(managerInfo);
    }


    @ApiOperation(value = "Id로 Manager(ROLE이 Trainer)조회", response = ManagerDto.class)
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<TrainerResponseDto> trainer_find(
            @PathVariable Long managerId
    ) {
        TrainerResponseDto byID = managerService.findByID(managerId);
        return ResponseEntity.ok(byID);
    }


    @ApiOperation(value = "GymId로 해당 Gym에 소속된 manager모두조회", response = ManagerDto.class)
    @GetMapping("/manager/findall/{gymId}")
    public ResponseEntity<List<TrainerResponseDto>> trainer_findall(
            @PathVariable Long gymId
    ) {
        List<TrainerResponseDto> trainerResponseDtos = managerService.trainer_findall(gymId);
        return ResponseEntity.ok(trainerResponseDtos);
    }



    @ApiOperation(value = "Manager 수정", response = String.class)
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER')")
    @PatchMapping("/manager")
    public ResponseEntity<String> update(
            @RequestBody ManagerDto managerDto
    ) {
        managerService.update(managerDto);
        return ResponseEntity.ok("Update!");
    }


    @ApiOperation(value = "Manager 삭제", response = String.class)
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER')")
    @DeleteMapping("/manager/{managerId}")
    public ResponseEntity<String> remove(@PathVariable Long managerId) {
        managerService.delete(managerId);
        return ResponseEntity.ok("Remove!");
    }


}
