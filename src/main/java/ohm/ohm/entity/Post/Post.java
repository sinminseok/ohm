package ohm.ohm.entity.Post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.dto.PostDto.PostDto;
import ohm.ohm.entity.Gym.Gym;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {


    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    //글 제목
    private String title;

    //글 내용
    private String content;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedBy
    private LocalDateTime lastModifiedTime;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    //글 이미지
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostImg> imgs;

    //Gym과 연관관계
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Builder
    public Post(String title, String content, Gym gym, LocalDateTime createdTime,String createdBy) {
        //this.getCreatedBy() = String.valueOf(LocalDate.now());
        this.title = title;
        this.createdTime = LocalDateTime.now();
        this.createdBy = createdBy;
        this.content = content;
        this.gym = gym;
    }

    public void update(Post post) {
        this.id = post.getId();
        this.lastModifiedTime = LocalDateTime.now();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

}
