package ohm.ohm.entity;


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

    //헬스장 사진
    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<GymImg> imgs;

    //트레이너가 가입시 해당 code로 인증후 어느 헬스장인지 식별
    private int code;

    //헬스장 소개 문구
    private String introduce;

    //헬스장 현재 인원
    private int current_count;

    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Manager> managers = new ArrayList<Manager>();


    @OneToMany(mappedBy = "gym",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Post> posts = new ArrayList<Post>();



    @Builder
    public Gym(String name,String address,int count,int code,String introduce){
        this.name = name;
        this.address = address;
        this.introduce = introduce;
        this.count = count;
        this.code = code;
    }

    //Gym에서 파일 처리를 위한 메서드
    public void addImg(GymImg gymImg){
        this.imgs.add(gymImg);

        if(gymImg.getGym() != this){
            gymImg.setGym(this);
        }
    }

}
