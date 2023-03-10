package ohm.ohm.repository.input;


import ohm.ohm.entity.Input.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface InputRepository extends JpaRepository<Input, Long> {

    @Query("select COALESCE(avg(i.count),0) from Input i where i.time = :time and i.gym.id = :gymId and i.date =:date")
    Double sumcount(@Param("time") String time, @Param("gymId") Long gymId,@Param("date") String date);

}
