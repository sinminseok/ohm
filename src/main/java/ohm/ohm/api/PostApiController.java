package ohm.ohm.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.PostDto.PostDto;
import ohm.ohm.dto.responseDto.PostResponseDto;
import ohm.ohm.service.GymService;
import ohm.ohm.service.ManagerService;
import ohm.ohm.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping("/api")
@Api(tags = {"POST API"})
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final ManagerService managerService;

    private final GymService gymService;


    //manager or trainer가 등록
    @ApiOperation(value = "Post 저장", response = Long.class)
    @PostMapping("/post/{gymId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER')")
    public ResponseEntity<Long> save(
            @PathVariable Long gymId,
            @Valid @RequestPart(value = "PostDto") PostDto para_postDto,
            @RequestPart(value = "images",required = false) List<MultipartFile> files
            ) throws Exception {

        Long save = postService.save(gymId,para_postDto,files);


        return ResponseEntity.ok(save);
    }

    //헬스장에 등록된 모든 Post 조회]
    @ApiOperation(value = "모든 Post 조회", response = PostResponseDto.class, responseContainer = "List")
    @GetMapping("/posts/{gymId}")
    public ResponseEntity<List<PostResponseDto>> findall(@PathVariable Long gymId){
        System.out.println();
        List<PostResponseDto> findall = postService.findall(gymId);
        return ResponseEntity.ok(findall);
    }

    //Post 조회 findByID
    @ApiOperation(value = "Post 조회", response = PostResponseDto.class)
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long postId){
        PostResponseDto byId = postService.findById(postId);
        return ResponseEntity.ok(byId);
    }






}
