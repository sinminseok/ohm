package ohm.ohm.dto.ManagerDto;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 3,max = 50)
    private String name;

    @NotNull
    @Size(min = 3,max = 50)
    private String password;
}
