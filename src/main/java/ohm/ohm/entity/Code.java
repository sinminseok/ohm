package ohm.ohm.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Code {

    @Id
    @GeneratedValue
    @Column(name = "code_id")
    private Long id;

    private String code;
}
