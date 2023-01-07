package ohm.ohm.entity;

import lombok.Getter;
import ohm.ohm.dto.PostDto;

import javax.persistence.*;

@Entity
@Getter
public class Post {


    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private String img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    public void update(PostDto postDto){
        this.id = postDto.getId();
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.img = postDto.getImg();
//        this.gym = postDto.gym;
    }

}
