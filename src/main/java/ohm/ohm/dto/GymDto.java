package ohm.ohm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Gym;

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

    private int current_count;

    private ManagerDto manager;

    private List<PostDto> posts = new ArrayList<PostDto>();

    private List<TrainerDto> trainers = new ArrayList<TrainerDto>();

    //테스트용 생성자
    public GymDto(String name,int count,int current_count){
        this.name = name;
        this.count = count;
        this.current_count = current_count;
    }

    //GymDto save entity 생성자ㅣ
    public GymDto(String name,String address,int count,ManagerDto managerDto){
        this.name = name;
        this.address = address;
        this.count = count;
        this.manager = managerDto;
    }

    public int increase_count(){
        System.out.println("호출ㄹ");
        System.out.println(this.current_count);
        this.current_count = this.current_count + 1;
        return current_count;
    }


    public int decrease_count(){
        this.current_count = this.current_count - 1;
        return current_count;
    }




}
