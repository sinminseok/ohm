package ohm.ohm.dto.responseDto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrainerResponseDto {

    private Long id;

    private String name;

    private String profile;

    private String oneline_introduce;
    //자기소개
    private String introduce;

    private String nickname;

    private int age;
}
