package ohm.ohm.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.ManagerDto.ManagerDto;
import ohm.ohm.dto.requestDto.ManagerRequestDto;
import ohm.ohm.jwt.TokenProvider;
import ohm.ohm.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
@Api(tags = {"Manager API"})
@RequiredArgsConstructor
public class CeoApiController {

    private final ManagerService managerService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @ApiOperation(value = "CEO code 인증(초기가입)", response = String.class)
    @PostMapping("/ceo/code/{code}")
    public ResponseEntity<String> check_code(
            @PathVariable String code) {

        boolean bool = managerService.check_code(code);
        if(bool == true){
            return ResponseEntity.ok("true");
        }else{
            return ResponseEntity.ok("false");
        }
    }


    @ApiOperation(value = "ceo 회원가입", response = ManagerDto.class)
    @PostMapping("/ceo")
    public ResponseEntity<ManagerDto> ceo_signup(@Valid @RequestBody ManagerRequestDto managerDto) {
        return ResponseEntity.ok(managerService.ceo_save(managerDto));
    }
}
