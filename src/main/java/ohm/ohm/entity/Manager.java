package ohm.ohm.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;



@Entity
@Getter
@NoArgsConstructor
public class Manager extends BaseTimeEntity{

    @Id
    @GeneratedValue()
    @Column(name = "manager_id")
    private Long id;

    private String name;

    private String password;

    //프로필사진
    private String profile;

    private String oneline_introduce;

    private String introduce;

    //실제이름
    private String nickname;

    private int age;

    private String email;


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


    public void update(Manager update){
        this.id = update.id;
        this.name = update.name;
        this.age = update.age;
        this.email = update.email;
    }


    //Manager 회원가입시 필요한 생성자
    public Manager(String name,String password,String nickname,String profile,String oneline_introduce,String introduce,Integer age,String email,Set<Authority> authorities){
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.oneline_introduce = oneline_introduce;
        this.introduce = introduce;
        this.age = age;
        this.email = email;
        this.authorities = authorities;
    }


}
