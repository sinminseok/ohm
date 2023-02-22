package ohm.ohm.dto.QuestionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.dto.AnswerDto.AnswerDto;

@Getter
@NoArgsConstructor
public class QuestionDto {

    private Long id;

    private String title;

    private String content;

    private AnswerDto answer;

}
