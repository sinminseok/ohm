package ohm.ohm.entity.Answer;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.dto.AnswerDto.AnswerDto;
import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Question.Question;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Answer {

    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    //헬스장이름
    private String content;

    @OneToOne(mappedBy = "answer")
    private Question question;

    @Builder
    public Answer(String content, Question question){
        this.content = content;
        this.question = question;
    }

    public void update(AnswerDto answerDto){
        this.content = answerDto.getContent();
    }


}
