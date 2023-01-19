package ohm.ohm.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class GymDto {

    private Long id;

    private String name;

    private String address;

    private int count;

    private String img;

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
