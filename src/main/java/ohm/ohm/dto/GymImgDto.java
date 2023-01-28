package ohm.ohm.dto;

import lombok.Builder;
import ohm.ohm.entity.Gym;

public class GymImgDto {

    private Long id;

    private int order;

    private Gym gym;

    private String origFileName;

    private String filePath;

    @Builder
    public GymImgDto(int order,String origFileName,String filePath){
        this.order = order;
        this.origFileName = origFileName;
        this.filePath = filePath;
    }
}
