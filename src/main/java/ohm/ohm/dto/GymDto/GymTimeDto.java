package ohm.ohm.dto.GymDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Gym.Gym;

import javax.persistence.OneToOne;


@Getter
@NoArgsConstructor
public class GymTimeDto {


    private Long id;



    //휴관일
    private String CLOSEDDAYS;

    private String SUNDAY;

    private String SATURDAY;

    //평일
    private String WEEKDAY;

    //공휴일
    private String HOLIDAY;

    @JsonIgnore
    private Gym gym;
}
