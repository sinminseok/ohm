package ohm.ohm.dto.InputDto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.dto.GymDto.GymDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class InputDto {

    private Long id;


    private LocalDateTime time;

    private String date;

    private int count;

    // input or output
    private String type;




}
