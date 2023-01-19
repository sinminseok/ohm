//package ohm.ohm.entity;
//
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import ohm.ohm.dto.PostDto;
//import ohm.ohm.dto.TrainerDto;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Getter
//@NoArgsConstructor
//public class Trainer {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "trainer_id")
//    private Long id;
//
//    private String name;
//
//    private String password;
//
//    private String profile;
//
//    @Enumerated(EnumType.STRING)
//    private Sex sex;
//
//    @ManyToOne
//    @JoinColumn(name = "gym_id")
//    private Gym gym;
//
//
//
//    public Trainer(String name,String password,Sex sex,Gym gym){
//        this.name = name;
//        this.password = password;
//        this.sex = sex;
//        this.gym  = gym;
//    }
//
//    public void update(TrainerDto trainerDto){
//        this.id = trainerDto.getId();
//        this.name = trainerDto.getName();
//        this.profile = trainerDto.getProfile();
////        this.gym = postDto.gym;
//    }
//}
