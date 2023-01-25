package ohm.ohm.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class GymDto extends BaseTimeDto{

    private Long id;
    @NotNull
    @Size(min = 1,max = 50)
    private String name;

    @NotNull
    @Size(min = 1,max = 50)
    private String address;

    //헬스장 총 인원
    private int count;

    private List<GymImgDto> img;

    @NotNull
    @Size(min = 1,max = 5)
    private int code;

    private int current_count;

    @JsonIgnore
    private ArrayList<ManagerDto> manager = new ArrayList<>();


    private List<PostDto> posts = new ArrayList<PostDto>();


    //테스트용 생성자
    public GymDto(String name,int count,int current_count){
        this.name = name;
        this.count = count;
        this.current_count = current_count;
    }

    //GymDto save entity 생성자ㅣ
    public GymDto(String name,String address,int count,int code){
        this.name = name;
        this.address = address;
        this.count = count;
        this.code = code;
        //this.manager = managerDto.getGymDto().getManager();
    }

    public int increase_count(){
        this.current_count = this.current_count + 1;
        return current_count;
    }


    public int decrease_count(){
        this.current_count = this.current_count - 1;
        return current_count;
    }




}
