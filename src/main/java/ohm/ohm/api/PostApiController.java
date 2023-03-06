package ohm.ohm.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.PostDto.PostDto;
import ohm.ohm.dto.responseDto.PostResponseDto;
import ohm.ohm.service.PostService;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
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


    //manager or trainer가 등록
    @ApiOperation(value = "Post 저장", response = Long.class)
    @PostMapping(value = "/post/{gymId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_CEO','ROLE_TRAINER')")
    public ResponseEntity<Long> save_dto(
            @PathVariable Long gymId,
            @Valid @RequestBody PostDto para_postDto
    ) throws Exception {
        Long save = postService.save_content(gymId, para_postDto);
        return ResponseEntity.ok(save);
    }

    //manager or trainer가 등록
    @ApiOperation(value = "Post image 저장", response = Long.class)
    @PostMapping(value = "/post/img/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_CEO','ROLE_TRAINER')")
    public ResponseEntity<Long> save_imgs(
            @PathVariable Long postId,
            @RequestPart(value = "images", required = false) List<MultipartFile> files
    ) throws Exception {
        Long save = postService.save_img(postId, files);
        return ResponseEntity.ok(save);
    }

    // 헬스장에 등록된 모든 Post 조회
    @ApiOperation(value = "모든 Post 조회", response = PostResponseDto.class, responseContainer = "List")
    @GetMapping("/posts/{gymId}")
    public ResponseEntity<Slice<PostResponseDto>> findall(@PathVariable Long gymId, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok(postService.findall(gymId, page));
    }

    //Post 조회 findByID
    @ApiOperation(value = "Post 조회", response = PostResponseDto.class)
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long postId) {
        PostResponseDto byId = postService.findById(postId);
        return ResponseEntity.ok(byId);
    }

    //Post 수정
    @ApiOperation(value = "Post 수정", response = String.class)
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER','ROLE_CEO')")
    @PatchMapping("/post")
    public ResponseEntity<String> update(
            @RequestBody PostDto postDto
    ) {
        postService.update_post(postDto);
        return ResponseEntity.ok("Update!");
    }


    //Post img 수정
    @ApiOperation(value = "PostIMG 수정", response = String.class)
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER','ROLE_CEO')")
    @PostMapping("/post/img/update/{postId}")
    public ResponseEntity<String> update_img(
            @RequestParam List<Long> imgIds,
            @PathVariable Long postId,
            @RequestPart(value = "images", required = false) List<MultipartFile> files
    ) throws Exception {
        postService.delete_imgs(imgIds);
        postService.save_img(postId, files);
        return ResponseEntity.ok("Remove!");
    }

    //Post 삭제
    @ApiOperation(value = "Post 삭제", response = String.class)
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER','ROLE_CEO')")
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> remove(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok("Remove!");
    }


}
