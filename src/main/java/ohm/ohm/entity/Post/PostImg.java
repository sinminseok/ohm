package ohm.ohm.entity.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohm.ohm.entity.Post.Post;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostImg {


    @Id
    @GeneratedValue
    @Column(name = "postimg_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //파일 원본명
    private String origFileName;

    //파일 저장 경로
    private String filePath;


    @Builder
    public PostImg(Post post,String origFileName,String filePath){
        this.post = post;
        this.origFileName = origFileName;
        this.filePath = filePath;
    }


}
