package ohm.ohm.entity.Input;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Input {

    @Id
    @GeneratedValue
    @Column(name = "input_id")
    private Long id;

    private String time;
}
