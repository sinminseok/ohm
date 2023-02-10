package ohm.ohm.entity.Gym;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import ohm.ohm.dto.GymDto.GymDto;
import ohm.ohm.entity.Manager.Manager;
import ohm.ohm.entity.Post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Gym{

    @Id
    @GeneratedValue
    @Column(name = "gym_id")
    private Long id;

    //헬스장이름
    private String name;

    //헬스장주소
    private String address;

    //헬스장 총인원
    private int count;

    //한줄소개
    private String oneline_introduce;


    //트레이너가 가입시 해당 code로 인증후 어느 헬스장인지 식별
    private int code;

    //헬스장 소개 문구
    private String introduce;


    //헬스장 면적수
    private String area;


    private int trainer_count;

    //헬스장 현재 인원
    private int current_count;

    //헬스장 사진
    @JsonIgnore
    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<GymImg> imgs;



    @OneToOne
    @JoinColumn(name = "gymtime_id")
    private GymTime gymTime;

    @JsonIgnore
    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Manager> managers = new ArrayList<Manager>();


    @JsonIgnore
    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Post> posts = new ArrayList<Post>();

    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<GymPrice> prices;

    public void register_time(GymTime gymTime){
        this.gymTime = gymTime;

    }

    public void update(GymDto gymDto){
        this.name = gymDto.getName();
        this.id = gymDto.getId();
        this.area = gymDto.getArea();
        this.oneline_introduce = gymDto.getOneline_introduce();
        this.trainer_count = gymDto.getTrainer_count();
        this.address = gymDto.getAddress();
        this.introduce = gymDto.getIntroduce();
        this.count = gymDto.getCount();
        this.code = gymDto.getCode();
    }

    @Builder
    public Gym(Long id,GymTime gymTime,String area,String name,String address,int count,int code,String introduce,String oneline_introduce,String holiday,String weekday_time,String weekend_time,int trainer_count){
        this.name = name;
        this.id = id;
        this.area = area;
        this.gymTime = gymTime;
        this.oneline_introduce = oneline_introduce;
        this.trainer_count = trainer_count;
        this.address = address;
        this.introduce = introduce;
        this.count = count;
        this.code = code;
    }


}
