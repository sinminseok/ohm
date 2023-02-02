package ohm.ohm.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import ohm.ohm.entity.Manager.Manager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//코무무 계정
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

    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "admin",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Manager> managers = new ArrayList<Manager>();
}
