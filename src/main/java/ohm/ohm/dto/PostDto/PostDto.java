package ohm.ohm.dto.PostDto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.dto.GymDto.GymDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostDto {

    private Long id;


    private String title;

    private String content;

    private List<PostImgDto> imgs;

    @JsonIgnore
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


    //Post 저장 생성자
    public PostDto(String title,String content,GymDto gymDto){
        this.id = id;
        this.title = title;
        this.content = content;
        this.gym = gymDto;
    }


}
