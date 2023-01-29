package ohm.ohm.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.dto.GymImgDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class GymResponseDto {

    private String name;

    private String address;

    private int count;

    private List<GymImgResponseDto> imgs;

    private String introduce;

    private String oneline_introduce;

    @Builder
    public GymResponseDto(String name,String address,int count,List<GymImgResponseDto> imgs,String introduce,String oneline_introduce){
        this.name = name;
        this.address = address;
        this.count = count;
        this.imgs = imgs;
        this.introduce = introduce;
        this.oneline_introduce = oneline_introduce;
    }
}
