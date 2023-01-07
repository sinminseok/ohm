package ohm.ohm.entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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


}
