package ohm.ohm.entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Gym {

    @Id
    @GeneratedValue
    @Column(name = "gym_id")
    private Long id;

    private String name;

    private String address;

    //헬스장 총인원
    private int count;

    private String img;

    //헬스장 현재 인원
    private int current_count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Post> posts = new ArrayList<Post>();

    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Trainer> trainers = new ArrayList<Trainer>();



}
