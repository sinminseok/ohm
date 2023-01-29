package ohm.ohm.api;


import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.GymDto;
import ohm.ohm.dto.ManagerDto;
import ohm.ohm.dto.responseDto.GymResponseDto;
import ohm.ohm.entity.Gym;
import ohm.ohm.service.GymService;
import ohm.ohm.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GymApiController {

    private final GymService gymService;
    private final ManagerService managerService;



    //로그인한 manager가 Gym정보를 입력하고 저장하는 메서드
    @PostMapping("/gym")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Long> save(
            @RequestPart(value = "images",required = false) List<MultipartFile> files,
            @Valid @RequestPart(value = "GymDto") GymDto gymDto

    ) throws Exception {

        ManagerDto managerDto = managerService.getMyManagerWithAuthorities();

        Long save = gymService.save(gymDto,files);

        managerService.register_gym(save, managerDto.getId());

        return ResponseEntity.ok(save);

    }

    //모든헬스장 조회
    @GetMapping("/gyms")
    public ResponseEntity<List<GymResponseDto>> findall() throws Exception{
        List<GymResponseDto> findall = gymService.findall();
        return ResponseEntity.ok(findall);
    }

    //이름으로 헬스장 조회
    @GetMapping("/gym/name/{gymName}")
    public ResponseEntity<List<GymResponseDto>> findByName(@PathVariable String gymName)  throws Exception{
        List<GymResponseDto> byName = gymService.findByName(gymName);
        return ResponseEntity.ok(byName);
    }

    //ID로 헬스장 조회(클라이언트에서 ID값을 가지고 있어야함)
    @GetMapping("/gym/{gymId}")
    public ResponseEntity<GymResponseDto> findById(@PathVariable Long gymId) throws Exception{
         return ResponseEntity.ok(gymService.findById(gymId));
    }


    //현재 헬스장 인원
    @GetMapping("/gym/count/{gymId}")
    public ResponseEntity<Integer> current_count(@PathVariable Long gymId) throws Exception {
        GymDto gymDto = gymService.findById_count(gymId);
        return ResponseEntity.ok(gymDto.getCurrent_count());

    }

    //현재 헬스장 인원수 증가 api
    @PostMapping("/gym/count-increase/{gymId}")
    public ResponseEntity<Integer> increase_count(@PathVariable Long gymId) throws Exception{
        gymService.increase_count(gymId);
        return ResponseEntity.ok(gymService.findById_count(gymId).getCurrent_count());
    }

    //헬스장 인원 감소 api
    @PostMapping("/gym/count-decrease/{gymId}")
    public ResponseEntity<Integer> decrease_count(@PathVariable Long gymId) throws Exception{
        gymService.decrease_count(gymId);

        return ResponseEntity.ok(gymService.findById_count(gymId).getCurrent_count());
    }


}
