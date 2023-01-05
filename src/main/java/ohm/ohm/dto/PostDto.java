package ohm.ohm.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private String img;

    private GymDto gym;
}
