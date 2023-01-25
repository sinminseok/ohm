package ohm.ohm.entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Gym extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "gym_id")
    private Long id;

    private String name;

    private String address;

    //헬스장 총인원
    private int count;

    //헬스장 사진
    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<GymImg> imgs;

    //트레이너가 가입시 해당 code로 인증후 어느 헬스장인지 식별
    private int code;

    //헬스장 현재 인원
    private int current_count;

    //cascade 랑 orphanRemoval을 함께 사용하면 부모 객체 삭제시 연관된 자식 객체도 삭제
    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Manager> managers = new ArrayList<Manager>();


    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Post> posts = new ArrayList<Post>();




}
