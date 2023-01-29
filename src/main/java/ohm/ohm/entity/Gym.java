package ohm.ohm.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Gym extends BaseTimeEntity{

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

    //헬스장 사진
    @JsonIgnore
    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<GymImg> imgs;

    //트레이너가 가입시 해당 code로 인증후 어느 헬스장인지 식별
    private int code;

    //헬스장 소개 문구
    private String introduce;

    //휴무일
    private String holiday;

    //평일 운영시간
    private String weekday_time;

    //주말 운영시간
    private String weekend_time;

    private int trainer_count;

    //헬스장 현재 인원
    private int current_count;

    @JsonIgnore
    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Manager> managers = new ArrayList<Manager>();


    @JsonIgnore
    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Post> posts = new ArrayList<Post>();



    @Builder
    public Gym(String name,String address,int count,int code,String introduce,String oneline_introduce,String holiday,String weekday_time,String weekend_time,int trainer_count){
        this.name = name;
        this.oneline_introduce = oneline_introduce;
        this.holiday = holiday;
        this.weekday_time = weekday_time;
        this.weekend_time = weekend_time;
        this.trainer_count = trainer_count;
        this.address = address;
        this.introduce = introduce;
        this.count = count;
        this.code = code;
    }


}
