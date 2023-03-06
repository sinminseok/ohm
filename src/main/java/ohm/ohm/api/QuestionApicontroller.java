package ohm.ohm.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ohm.ohm.dto.ManagerDto.ManagerDto;
import ohm.ohm.dto.QuestionDto.QuestionDto;
import ohm.ohm.dto.requestDto.GymRequestDto;
import ohm.ohm.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"Question API"})
@RequiredArgsConstructor
public class QuestionApicontroller {

    private final QuestionService questionService;

    @ApiOperation(value = "Question 등록 일반사용자 사용가능", response = Long.class)
    @PostMapping("/question/{gymId}")
    public ResponseEntity<Long> save(
            @PathVariable Long gymId,
            @Valid @RequestBody QuestionDto questionDto

    ) throws Exception {

        Long aLong = questionService.save_question(gymId, questionDto);
        return ResponseEntity.ok(aLong);

    }


    @ApiOperation(value = "Question 한개 조회", response = QuestionDto.class)
    @GetMapping("/question/{questionId}")
    public ResponseEntity<QuestionDto> findById(
            @PathVariable Long questionId

    ) throws Exception {
        QuestionDto question = questionService.find_question(questionId);
        return ResponseEntity.ok(question);

    }



    @ApiOperation(value = "Question 모두 조회", response = QuestionDto.class,responseContainer = "List")
    @GetMapping("/question/all/{gymId}")
    public ResponseEntity<List<QuestionDto>> findall(
            @PathVariable Long gymId

    ) throws Exception {

        List<QuestionDto> questionDtos = questionService.findall_question(gymId);
        return ResponseEntity.ok(questionDtos);

    }


    @ApiOperation(value = "Question 삭제", response = String.class)
    @DeleteMapping("/question/{questionId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_TRAINER','ROLE_CEO')")
    public ResponseEntity<String> delete(
            @PathVariable Long questionId

    ) throws Exception {

        questionService.delete_question(questionId);
        return ResponseEntity.ok("DELETE!");

    }
}
