package ohm.ohm.entity.Manager;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//인가에 사용되는 권한들을 DB로 관리하고자 생성한 엔티티이다.
//Manager는 인증에 사용되는 계정 엔티티입니다.
@Entity
@Getter
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "authority_name",length = 50)
    private String authorityName;

    @Builder
    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }
}
