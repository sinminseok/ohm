package ohm.ohm.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PostImg {


    @Id
    @GeneratedValue
    @Column(name = "postimg_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //사진 순서
    private String img;
}
