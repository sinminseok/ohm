package ohm.ohm.entity.Question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Answer.Answer;
import ohm.ohm.entity.Gym.Gym;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "question_id")
    private Long id;

    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @Builder
    public Question(String content,Gym gym,Answer answer){
        this.content = content;
        this.gym = gym;
        this.answer = answer;
    }

    public void register_answer(Answer answer){
        this.answer = answer;
    }
}
