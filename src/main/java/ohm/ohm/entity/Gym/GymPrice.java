package ohm.ohm.entity.Gym;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class GymPrice {

    @Id
    @GeneratedValue
    @Column(name = "gymprice_id")
    private Long id;

    private String during;

    private String price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Builder
    public GymPrice(String during,String price,Gym gym){
        this.during = during;
        this.price = price;
        this.gym = gym;
    }
}
