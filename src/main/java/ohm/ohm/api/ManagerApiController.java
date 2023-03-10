package ohm.ohm.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.ManagerDto.LoginDto;
import ohm.ohm.dto.ManagerDto.ManagerDto;
import ohm.ohm.dto.ManagerDto.TokenDto;
import ohm.ohm.dto.PostDto.PostDto;
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
public class ManagerApiController {

    private final ManagerService managerService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @ApiOperation(value = "manager 회원가입", response = ManagerDto.class)
    @PostMapping("/manager/{gymId}")
    public ResponseEntity<ManagerDto> manager_signup(
            @PathVariable Long gymId,
            @Valid @RequestBody ManagerRequestDto managerDto) {
        return ResponseEntity.ok(managerService.manager_save(managerDto,gymId));
    }






}
