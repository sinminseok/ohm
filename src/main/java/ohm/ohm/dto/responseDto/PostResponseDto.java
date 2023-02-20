package ohm.ohm.dto.responseDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long id;

    private String title;

    private String content;

    private List<PostImgResponseDto> imgs;
}
