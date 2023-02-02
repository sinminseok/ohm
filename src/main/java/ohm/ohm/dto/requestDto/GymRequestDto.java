package ohm.ohm.dto.requestDto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GymRequestDto {

    private String name;

    private String address;

    private int count;

    private String oneline_introduce;

    //트레이너가 가입시 해당 code로 인증후 어느 헬스장인지 식별
    private int code;

    //헬스장 소개 문구
    private String introduce;

    //헬스장 면적수
    private String area;

    private int trainer_count;

    //헬스장 현재 인원
    private int current_count;

    //휴관일
    private String CLOSEDDAYS;

    private String SUNDAY;

    private String SATURDAY;

    //평일
    private String WEEKDAY;

    //공휴일
    private String HOLIDAY;

}
