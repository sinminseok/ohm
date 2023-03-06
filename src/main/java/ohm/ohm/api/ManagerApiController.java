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
import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Manager.Authority;
import ohm.ohm.entity.Manager.Manager;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
