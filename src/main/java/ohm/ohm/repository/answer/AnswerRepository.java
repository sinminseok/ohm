package ohm.ohm.repository.answer;

import ohm.ohm.entity.Answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
