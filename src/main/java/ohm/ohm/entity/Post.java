package ohm.ohm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Post extends BaseEntity{


    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    //추후 이미지 list로 변경
    @OneToMany(mappedBy = "post",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<PostImg> imgs;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    public void update(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imgs = post.getImgs();
    }

}
