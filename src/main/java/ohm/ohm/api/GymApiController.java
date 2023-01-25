package ohm.ohm.api;


import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.GymDto;
import ohm.ohm.dto.GymImgDto;
import ohm.ohm.dto.ManagerDto;
import ohm.ohm.entity.Manager;
import ohm.ohm.service.GymService;
import ohm.ohm.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Long> save(@Valid @RequestBody GymDto gymDto){
        ManagerDto managerDto = managerService.getMyManagerWithAuthorities();
        GymDto saveGymDto = new GymDto(gymDto.getName(),gymDto.getAddress(),gymDto.getCount(),gymDto.getCode());
        Long save = gymService.save(saveGymDto);
        managerService.register_gym(save, managerDto.getId());
        return ResponseEntity.ok(save);
    }

//    @PostMapping("/gym/{gymId}/img")
//    public ResponseEntity<Long> save_img(@Valid @RequestBody GymImgDto gymImgDto,@PathVariable Long gymId) throws Exception {
//        gymService.findById(gymId)
//        gymService.save_img()
//    }

    //모든헬스장 조회
    @GetMapping("/gym")
    public ResponseEntity<List<GymDto>> findall() throws Exception{
        List<GymDto> findall = gymService.findall();
        return ResponseEntity.ok(findall);
    }

    //이름으로 헬스장 조회
    @GetMapping("/gym/name/{gymName}")
    public ResponseEntity<List<GymDto>> findByName(@PathVariable String gymName)  throws Exception{
        List<GymDto> byName = gymService.findByName(gymName);
        return ResponseEntity.ok(byName);
    }

    //ID로 헬스장 조회(클라이언트에서 ID값을 가지고 있어야함)
    @GetMapping("/gym/{gymId}")
    public ResponseEntity<GymDto> findById(@PathVariable Long gymId) throws Exception{
         return ResponseEntity.ok(gymService.findById(gymId));
    }


    //현재 헬스장 인원
    @GetMapping("/gym/count/{gymId}")
    public ResponseEntity<Integer> current_count(@PathVariable Long gymId) throws Exception {
        GymDto gymDto = gymService.findById(gymId);
        return ResponseEntity.ok(gymDto.getCurrent_count());
    }

    //현재 헬스장 인원수 증가 api
    @PostMapping("/gym/count-increase/{gymId}")
    public ResponseEntity<Integer> increase_count(@PathVariable Long gymId) throws Exception{
        gymService.increase_count(gymId);
        return ResponseEntity.ok(gymService.findById(gymId).getCurrent_count());

    }

    //헬스장 인원 감소 api
    @PostMapping("/gym/count-decrease/{gymId}")
    public ResponseEntity<Integer> decrease_count(@PathVariable Long gymId) throws Exception{
        gymService.decrease_count(gymId);

        return ResponseEntity.ok(gymService.findById(gymId).getCurrent_count());
    }


}
