package ohm.ohm.entity.Statistics;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Gym.Gym;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Statistics {

    @Id
    @GeneratedValue
    @Column(name = "statistics_id")
    private Long id;


    private double one;
    private double two;
    private double three;
    private double four;
    private double five;
    private double six;
    private double seven;
    private double eight;
    private double nine;
    private double ten;
    private double eleven;
    private double twelve;
    private double thirteen;
    private double fourteen;
    private double fifteen;
    private double sixteen;
    private double seventeen;
    private double eighteen;
    private double nineteen;
    private double twenty;
    private double twenty_one;
    private double twenty_two;
    private double twenty_three;
    private double twenty_four;

    @OneToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Builder
    public Statistics(Gym gym) {
        this.gym = gym;
        this.one = 0;
        this.two = 0;
        this.three = 0;
        this.four = 0;
        this.five = 0;
        this.six = 0;
        this.seven = 0;
        this.eight = 0;
        this.nine = 0;
        this.ten = 0;
        this.eleven = 0;
        this.twelve = 0;
        this.thirteen = 0;
        this.fourteen = 0;
        this.fifteen = 0;
        this.sixteen = 0;
        this.seventeen = 0;
        this.eighteen = 0;
        this.nineteen = 0;
        this.twenty = 0;
        this.twenty_one = 0;
        this.twenty_two = 0;
        this.twenty_three = 0;
        this.twenty_four = 0;


    }


}
