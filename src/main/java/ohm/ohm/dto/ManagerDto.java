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
public class ManagerDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private Integer age;

    private AdminDto admin;

    private GymDto gymDto;

    private Set<Authority> authorities;

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
