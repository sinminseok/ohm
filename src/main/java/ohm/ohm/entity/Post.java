package ohm.ohm.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post {


    @Id
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private String img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;


}
