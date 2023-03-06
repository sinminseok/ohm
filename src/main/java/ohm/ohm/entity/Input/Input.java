package ohm.ohm.entity.Input;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Gym.Gym;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Input {

    @Id
    @GeneratedValue
    @Column(name = "input_id")
    private Long id;

    private String time;

    private String date;

    private int count;

    // input or output
    private String type;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;


    @Builder
    public Input(String time,String date,int count,Gym gym,String type){
        this.time = time;
        this.type = type;
        this.date = date;
        this.count = count;
        this.gym = gym;
    }
}
