package ohm.ohm.entity;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Trainer {


    @Id
    @Column(name = "post_id")
    private Long id;

    private String name;

    private String profile;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;
}
