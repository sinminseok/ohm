package ohm.ohm.entity.Gym;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Enum.Day;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GymTime {

    @Id
    @GeneratedValue
    @Column(name = "gymtime_id")
    private Long id;

    //휴관일
    private String CLOSEDDAYS;

    private String SUNDAY;

    private String SATURDAY;

    //평일
    private String WEEKDAY;

    //공휴일
    private String HOLIDAY;

    @OneToOne(mappedBy = "gymTime")
    private Gym gym;

    @Builder
    public GymTime(Gym gym,String CLOSEDDAYS,String SUNDAY,String SATURDAY,String WEEKDAY,String HOLIDAY){
        this.CLOSEDDAYS =CLOSEDDAYS;
        this.SUNDAY = SUNDAY;
        this.gym = gym;
        this.SATURDAY = SATURDAY;
        this.WEEKDAY = WEEKDAY;
        this.HOLIDAY = HOLIDAY;
    }




}


