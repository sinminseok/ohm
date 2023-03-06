package ohm.ohm.entity.Manager;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.dto.ManagerDto.ManagerDto;
import ohm.ohm.entity.Admin;
import ohm.ohm.entity.Gym.Gym;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


// ROLE은 MANAGER,TRAINER 두개로 구분
//MANAGER는 헬스장 총 책임자(사장)
//TRAINER는 헬스장 소속 트레이너(직원)
@Entity
@Getter
@NoArgsConstructor
public class Manager {


    @Id
    @GeneratedValue()
    @Column(name = "manager_id")
    private Long id;

    private String name;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedBy
    private LocalDateTime lastModifiedTime;

    private String password;

    //프로필사진
    private String profileUrl;


    //프사이름
    private String profileOrignName;

    //한줄소개
    private String oneline_introduce;

    //자기소개
    private String introduce;

    private String position;

    //실제이름
    private String nickname;


    @ManyToMany
    @JoinTable( // JoinTable은 테이블과 테이블 사이에 별도의 조인 테이블을 만들어 양 테이블간의 연관관계를 설정 하는 방법
            name = "account_authority",
            joinColumns = {@JoinColumn(name = "manager_id", referencedColumnName = "manager_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    public void register_profile(String profile, String profileOrignName) {
        this.profileUrl = profile;
        this.profileOrignName = profileOrignName;
    }


    public void update(ManagerDto manager) {

        this.position = manager.getPosition();
        this.name = manager.getName();
        this.lastModifiedTime = LocalDateTime.now();
        this.nickname = manager.getNickname();
        this.oneline_introduce = manager.getOneline_introduce();
        this.introduce = manager.getIntroduce();
    }

    @Builder
    public Manager(String position, Gym gym, String name, String profileOrignName, String password, String nickname, String profile, String oneline_introduce, String introduce, Integer age, String email, Set<Authority> authorities) {
        this.name = name;
        this.position = position;
        this.gym = gym;
        this.profileOrignName = profileOrignName;
        this.createdTime = LocalDateTime.now();
        this.password = password;
        this.nickname = nickname;
        this.profileUrl = profile;
        this.oneline_introduce = oneline_introduce;
        this.introduce = introduce;
        this.authorities = authorities;
    }


}
