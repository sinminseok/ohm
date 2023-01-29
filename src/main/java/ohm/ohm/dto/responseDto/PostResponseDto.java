package ohm.ohm.dto.responseDto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private String title;

    private String content;

    private List<PostImgResponseDto> imgs;


    @Builder
    public PostResponseDto(String title,String content,List<PostImgResponseDto> imgs){
        this.title = title;
        this.content = content;
        this.imgs = imgs;
    }
}
