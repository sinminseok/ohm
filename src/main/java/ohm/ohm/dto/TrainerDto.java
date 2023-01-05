package ohm.ohm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Sex;

@Getter
@NoArgsConstructor
public class TrainerDto {

    private Long id;

    private String name;

    private String profile;

    private Sex sex;

    private GymDto gymDto;
}
