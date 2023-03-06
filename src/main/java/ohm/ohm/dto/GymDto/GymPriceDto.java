package ohm.ohm.dto.GymDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class GymPriceDto {

    private Long id;

    private String during;

    private String price;

//    @JsonIgnore
//    private GymDto gym;
}
