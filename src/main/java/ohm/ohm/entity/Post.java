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

    //글 제목
    private String title;

    //글 내용
    private String content;

    //글 이미지
    @OneToMany(mappedBy = "post",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<PostImg> imgs;

    //Gym과 연관관계
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
