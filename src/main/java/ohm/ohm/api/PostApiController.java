package ohm.ohm.api;


import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.GymDto;
import ohm.ohm.dto.ManagerDto;
import ohm.ohm.dto.PostDto;
import ohm.ohm.service.GymService;
import ohm.ohm.service.ManagerService;
import ohm.ohm.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.List;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final ManagerService managerService;
    private final GymService gymService;

    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //manager or trainer가 등록
    @PostMapping("/post/{gymId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER')")
    public ResponseEntity<String> save(@PathVariable Long gymId, @Valid @RequestBody PostDto para_postDto) throws Exception {
        ManagerDto managerDto = managerService.getMyManagerWithAuthorities();

        GymDto byId = gymService.findById(gymId);

        PostDto postDto = new PostDto(para_postDto.getTitle(),para_postDto.getContent(),byId);
        Long save = postService.save(postDto);
        return ResponseEntity.ok("success");
    }

    //헬스장에 등록된 모든 Post 조회
    @GetMapping("/posts/{gymId}")
    public ResponseEntity<List<PostDto>> findall(@PathVariable Long gymId){
        List<PostDto> findall = postService.findall(gymId);
        return ResponseEntity.ok(findall);
    }

    //Post 조회 findByID
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> findById(@PathVariable Long postId){
        PostDto byId = postService.findById(postId);
        return ResponseEntity.ok(byId);
    }






}
