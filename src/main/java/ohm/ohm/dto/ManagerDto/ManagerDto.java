package ohm.ohm.dto.ManagerDto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.dto.AdminDto;
import ohm.ohm.dto.GymDto.GymDto;
import ohm.ohm.entity.Manager.Authority;

import java.util.Set;

@Getter
@NoArgsConstructor
public class ManagerDto {

    private Long id;

    private String name;

    private String position;

    private String email;

    private String nickname;

    private String profile;

    private String oneline_introduce;

    private String introduce;

    private int age;

    private AdminDto admin;

    private GymDto gymDto;

    private Set<Authority> authorities;

    @Builder
    public ManagerDto(String profile, String oneline_introduce, String introduce, String nickname, Long id, String name, GymDto gymDto, Set<Authority> authorities) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.profile = profile;
        this.oneline_introduce = oneline_introduce;
        this.introduce = introduce;
        this.gymDto = gymDto;
        this.authorities = authorities;
    }

    //
    public ManagerDto(Long id, String name, String email, String password, Integer age, GymDto gymDto, Set<Authority> authorities) {
        this.id = id;
        this.name = name;
        this.gymDto = gymDto;
        this.authorities = authorities;
    }

    public ManagerDto(String name, String email) {
        this.name = name;
    }

    public ManagerDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
    }
}
