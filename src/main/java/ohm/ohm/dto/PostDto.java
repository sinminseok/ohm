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

    public PostDto(String title,String content){
        this.title = title;
        this.content = content;
    }

    public PostDto(Long id,String title,String content){
        this.id =id;
        this.title = title;
        this.content = content;
    }


}
