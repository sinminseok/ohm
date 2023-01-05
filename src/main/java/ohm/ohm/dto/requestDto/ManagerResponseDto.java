package ohm.ohm.dto.requestDto;


import lombok.Data;

@Data
public class ManagerResponseDto {

    private Long id;

    private String email;


    private String name;

    public ManagerResponseDto(Long id, String email,String name){
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
