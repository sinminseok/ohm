package ohm.ohm.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



@Entity
@Getter
@NoArgsConstructor
public class Manager{

    @Id
    @GeneratedValue
    @Column(name = "manager_id")
    private Long id;

    //이름
    private String name;

    private Integer age;

    //email로 회원가입
    private String email;

    private String password;

    @ManyToMany
    @JoinTable( // JoinTable은 테이블과 테이블 사이에 별도의 조인 테이블을 만들어 양 테이블간의 연관관계를 설정 하는 방법
            name = "account_authority",
            joinColumns = {@JoinColumn(name = "manager_id", referencedColumnName = "manager_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    //cascade 랑 orphanRemoval을 함께 사용하면 부모 객체 삭제시 연관된 자식 객체도 삭제
    @OneToMany(mappedBy = "manager",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Gym> gyms = new ArrayList<Gym>();

    public void update(Manager update){
        this.id = update.id;
        this.name = update.name;
        this.age = update.age;
        this.email = update.email;
    }

    public Manager(String name,String password,Integer age,String email,Set<Authority> authorities){
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.authorities = authorities;
    }


}
