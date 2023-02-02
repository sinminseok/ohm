package ohm.ohm.entity.Gym;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Gym.Gym;

import javax.persistence.*;

@Entity
@Getter
//@Table(name = "")
@NoArgsConstructor
public class GymImg {

    @Id
    @GeneratedValue
    @Column(name = "gymimg_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    //파일 원본명
    private String origFileName;

    //파일 저장 경로
    private String filePath;

    @Builder
    public GymImg(Gym gym, String origFileName, String filePath) {
        this.origFileName = origFileName;
        this.gym = gym;
        this.filePath = filePath;
    }


}
