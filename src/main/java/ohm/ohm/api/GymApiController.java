package ohm.ohm.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.GymDto.GymDto;
import ohm.ohm.dto.GymDto.GymPriceDto;
import ohm.ohm.dto.GymDto.GymTimeDto;
import ohm.ohm.dto.ManagerDto.ManagerDto;
import ohm.ohm.dto.PostDto.PostDto;
import ohm.ohm.dto.requestDto.GymRequestDto;
import ohm.ohm.dto.responseDto.GymResponseDto;
import ohm.ohm.service.GymService;
import ohm.ohm.service.InputService;
import ohm.ohm.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"Gym API"})
@RequiredArgsConstructor
public class GymApiController {

    private final GymService gymService;
    private final InputService inputService;
    private final ManagerService managerService;



    @ApiOperation(value = "Gym 등록(Manager만 사용)", response = Long.class)
    @PostMapping("/gym")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Long> save(
            @Valid @RequestBody GymRequestDto gymRequestDto
    ) throws Exception {

        ManagerDto managerDto = managerService.getMyManagerWithAuthorities();

        Long save = gymService.save(gymRequestDto);

        managerService.register_gym(save, managerDto.getId());

        return ResponseEntity.ok(save);

    }

    //로그인한 manager가 Gym정보를 입력하고 저장하는 메서드
    @ApiOperation(value = "GymImg 등록(Manager만 사용)", response = Long.class)
    @PostMapping("/gym/image/{gymId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Long> save_img(
            @PathVariable Long gymId,
            @RequestPart(value = "images",required = false) List<MultipartFile> files
    ) throws Exception {

        Long aLong = gymService.save_img(gymId, files);
        return ResponseEntity.ok(aLong);
    }

    //모든헬스장 조회
    @ApiOperation(value = "모든 Gym 조회", response = GymResponseDto.class , responseContainer = "List")
    @GetMapping("/gyms")
    public ResponseEntity<List<GymResponseDto>> findall() throws Exception{
        List<GymResponseDto> findall = gymService.findall();
        return ResponseEntity.ok(findall);
    }

    @GetMapping("/gym/avg/{gymId}")
    public ResponseEntity<List<String>> search_avg(
            @PathVariable Long gymId

    ) throws Exception{
        List<String> value = inputService.get_value(gymId);
        return ResponseEntity.ok(value);
    }


    //이름으로 헬스장 조회
    @ApiOperation(value = "이름으로 헬스장 조회", response = GymResponseDto.class , responseContainer = "List")
    @GetMapping("/gym/name/{gymName}")
    public ResponseEntity<List<GymResponseDto>> findByName(@PathVariable String gymName)  throws Exception{
        List<GymResponseDto> byName = gymService.findByName(gymName);
        return ResponseEntity.ok(byName);
    }

    //ID로 헬스장 조회
    @ApiOperation(value = "ID로 헬스장 조회", response = GymResponseDto.class)
    @GetMapping("/gym/{gymId}")
    public ResponseEntity<GymResponseDto> findById(@PathVariable Long gymId) throws Exception{
         return ResponseEntity.ok(gymService.findById(gymId));
    }


    //현재 헬스장 인원
    @ApiOperation(value = "현재 Gym에 있는 인원조회", response = Integer.class)
    @GetMapping("/gym/count/{gymId}")
    public ResponseEntity<Integer> current_count(@PathVariable Long gymId) throws Exception {
        GymDto gymDto = gymService.findById_count(gymId);
        return ResponseEntity.ok(gymDto.getCurrent_count());

    }

    //현재 헬스장 인원수 증가 api
    @ApiOperation(value = "현재 Gym 인원증가", response = Integer.class)
    @PostMapping("/gym/count-increase/{gymId}")
    public ResponseEntity<Integer> increase_count(@PathVariable Long gymId) throws Exception{
        gymService.increase_count(gymId);
        int current_count = gymService.findById_count(gymId).getCurrent_count();
        inputService.insert_data(current_count,gymId);
        return ResponseEntity.ok(current_count);
    }

    //헬스장 인원 감소 api
    @ApiOperation(value = "현재 Gym 인원감소", response = Integer.class)
    @PostMapping("/gym/count-decrease/{gymId}")
    public ResponseEntity<Integer> decrease_count(@PathVariable Long gymId) throws Exception{
        gymService.decrease_count(gymId);
        return ResponseEntity.ok(gymService.findById_count(gymId).getCurrent_count());
    }


    @ApiOperation(value = "code로 GymId조회", response = Integer.class)
    @GetMapping("/gym/code/{code}")
    public ResponseEntity<Long> check_code(@PathVariable int code) throws Exception{
        Long aLong = gymService.check_code(code);
        return ResponseEntity.ok(aLong);
    }

    @ApiOperation(value = "gym price등록", response = Integer.class)
    @PostMapping("/gym/price/{gymId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Long> register_price(
            @RequestBody GymPriceDto gymPriceDto,
            @PathVariable Long gymId) throws Exception{
        Long aLong = gymService.register_price(gymId, gymPriceDto);
        return ResponseEntity.ok(aLong);
    }

    @ApiOperation(value = "gym Time등록", response = Integer.class)
    @PostMapping("/gym/time/{gymId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Long> register_time(
            @RequestBody GymTimeDto gymTimeDto,
            @PathVariable Long gymId) throws Exception{
        Long aLong = gymService.register_time(gymId, gymTimeDto);
        return ResponseEntity.ok(aLong);
    }

    //Post 수정
    @ApiOperation(value = "Gym 수정", response = String.class)
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER')")
    @PatchMapping("/gym")
    public ResponseEntity<String> update(
            @RequestBody GymDto gymDto
    ) {
        gymService.update_gym(gymDto);
        return ResponseEntity.ok("Update!");
    }



}
