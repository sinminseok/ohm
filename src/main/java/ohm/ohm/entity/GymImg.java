package ohm.ohm.entity;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class GymImg {

    @Id
    @GeneratedValue
    @Column(name = "gymimg_id")
    private Long id;

    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    private String img;

}
