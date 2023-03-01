package ohm.ohm.dto.responseDto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CountResponseDto {

    private int count;

    private String avgCount;

    @Builder
    public CountResponseDto(int count,String avgCount){
        this.count = count;
        this.avgCount = avgCount;
    }
}
