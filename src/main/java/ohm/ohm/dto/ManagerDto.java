package ohm.ohm.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Gym;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ManagerDto {

    private Long id;

    private String email;

    private String password;

    private Integer age;

    private AdminDto admin;

    private List<GymDto> gyms = new ArrayList<GymDto>();
}
