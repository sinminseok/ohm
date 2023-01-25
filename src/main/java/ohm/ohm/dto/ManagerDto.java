package ohm.ohm.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Authority;
import ohm.ohm.entity.Gym;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ManagerDto extends BaseTimeDto{

    private Long id;

    private String name;

    private String email;

    private String password;

    private String nickname;

    private String profile;

    private String oneline_introduce;

    private String introduce;

    private int age;

    private AdminDto admin;

    private GymDto gymDto;

    private Set<Authority> authorities;

    //
    public ManagerDto(Long id,String name,String email,String password,Integer age,GymDto gymDto,Set<Authority> authorities){
        this.id = id;
        this.name = name;
        this.password = password;
        this.age=age;
        this.gymDto = gymDto;
        this.authorities = authorities;
    }

    public ManagerDto(String name,String email){
        this.name = name;
        this.email = email;
    }

    public ManagerDto(Long id,String name,String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
