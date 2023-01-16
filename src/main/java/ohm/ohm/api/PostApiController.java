package ohm.ohm.api;


import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.GymDto;
import ohm.ohm.dto.ManagerDto;
import ohm.ohm.dto.PostDto;
import ohm.ohm.service.GymService;
import ohm.ohm.service.ManagerService;
import ohm.ohm.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final ManagerService managerService;

    private GymService gymService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/post/save/{gymId}")
    public ResponseEntity<String> save(@PathVariable Long gymId, @Valid @RequestBody PostDto para_postDto) throws Exception {
        ManagerDto managerDto = managerService.getMyManagerWithAuthorities();
        GymDto byId = gymService.findById(gymId);
        PostDto postDto = new PostDto(para_postDto.getTitle(),para_postDto.getContent(),byId);
        Long save = postService.save(postDto);
        return ResponseEntity.ok("success");
    }

//    @GetMapping("/post/findall/{gymId}")
//    public ResponseEntity<> findall(@PathVariable Long gymId){
//        postService.findall()
//    }


}
