package ohm.ohm.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Admin {

    @Id
    @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    private String name;

    private String password;

    private String nickname;

    //email로 회원가입
    private String email;

    //manager 연관관계 매핑
    @JsonIgnore
    @OneToMany(mappedBy = "admin",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Manager> managers = new ArrayList<Manager>();
}
