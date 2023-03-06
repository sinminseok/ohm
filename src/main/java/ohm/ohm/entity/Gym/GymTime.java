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
    private String closeddays;

    private String sunday;

    private String saturday;

    //평일
    private String weekday;

    //공휴일
    private String holiday;

    @OneToOne(mappedBy = "gymTime")
    private Gym gym;

    @Builder
    public GymTime(Gym gym,String CLOSEDDAYS,String SUNDAY,String SATURDAY,String WEEKDAY,String HOLIDAY){
        this.closeddays =CLOSEDDAYS;
        this.sunday = SUNDAY;
        this.gym = gym;
        this.saturday = SATURDAY;
        this.weekday = WEEKDAY;
        this.holiday = HOLIDAY;
    }

    public void update(GymTime gymTime){

        this.id = gymTime.getId();
        this.sunday = gymTime.sunday;
       // this.gym = gym;
        this.closeddays = gymTime.closeddays;
        this.saturday = gymTime.saturday;
        this.holiday = gymTime.holiday;
        this.weekday = gymTime.weekday;

    }




}


