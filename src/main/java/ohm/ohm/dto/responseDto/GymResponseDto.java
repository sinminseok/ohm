package ohm.ohm.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GymResponseDto {

    private Long id;

    private String name;

    private String address;

    private int count;

    private int current_count;

    private List<GymImgResponseDto> imgs;

    private String introduce;

    private String oneline_introduce;

    @Builder
    public GymResponseDto(Long id,int current_count,String name,String address,int count,List<GymImgResponseDto> imgs,String introduce,String oneline_introduce){
        this.name = name;
        this.id = id;
        this.current_count = current_count;
        this.address = address;
        this.count = count;
        this.imgs = imgs;
        this.introduce = introduce;
        this.oneline_introduce = oneline_introduce;
    }
}
