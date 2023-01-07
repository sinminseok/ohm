package ohm.ohm.entity;


import lombok.Getter;
import ohm.ohm.dto.PostDto;
import ohm.ohm.dto.TrainerDto;

import javax.persistence.*;

@Entity
@Getter
public class Trainer {


    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String name;

    private String profile;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    public void update(TrainerDto trainerDto){
        this.id = trainerDto.getId();
        this.name = trainerDto.getName();
        this.profile = trainerDto.getProfile();
//        this.gym = postDto.gym;
    }
}
