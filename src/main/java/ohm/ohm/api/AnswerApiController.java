package ohm.ohm.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.AnswerDto.AnswerDto;
import ohm.ohm.dto.ManagerDto.ManagerDto;
import ohm.ohm.dto.requestDto.GymRequestDto;
import ohm.ohm.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(tags = {"Answer API"})
@RequiredArgsConstructor
public class AnswerApiController {

    private final AnswerService answerService;



    @ApiOperation(value = "Answer 등록", response = Long.class)
    @PostMapping("/answer/{questionId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER','ROLE_CEO')")
    public ResponseEntity<Long> save(
            @Valid @RequestBody AnswerDto answerDto,
            @PathVariable Long questionId

    ) throws Exception {
        AnswerDto save = answerService.save(answerDto, questionId);
        return ResponseEntity.ok(save.getId());
    }

    @ApiOperation(value = "Answer 수정", response = String.class)
    @PatchMapping("/answer/{answerId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER','ROLE_CEO')")
    public ResponseEntity<String> update(
            @Valid @RequestBody AnswerDto answerDto,
            @PathVariable Long answerId

    ) throws Exception {
        answerService.update(answerId, answerDto);
        return ResponseEntity.ok("Update!");
    }


    @ApiOperation(value = "Answer 삭제", response = String.class)
    @DeleteMapping("/answer/{answerId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER','ROLE_CEO')")
    public ResponseEntity<String> delete(
            @PathVariable Long answerId

    ) throws Exception {
        answerService.delete(answerId);
        return ResponseEntity.ok("DELETE");
    }

}
