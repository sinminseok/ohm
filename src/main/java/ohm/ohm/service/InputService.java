package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Input.Input;
import ohm.ohm.repository.gym.GymRepository;
import ohm.ohm.repository.input.InputRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InputService {

    private final InputRepository inputRepository;
    private final GymRepository gymRepository;
    Calendar cal = Calendar.getInstance();

    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

    public String dayofweek() {
        String day = null;
        switch (dayOfWeek) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;

        }
        return day;
    }




    @Transactional
    public Long insert_data(int count, Long gymId,String type) {
        Optional<Gym> gym = gymRepository.findById(gymId);

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        String formatedNow = now.format(formatter);

        String hour;
        if (formatedNow.substring(0, 1).equals("0")) {
            hour = formatedNow.substring(1);
        } else {
            hour = formatedNow.toString();
        }

        var day = dayofweek();
        Input input = Input.builder()
                .date(day)
                .type(type)
                .count(count)
                .gym(gym.get())
                .time(hour)
                .build();

        Input save = inputRepository.save(input);
        return save.getId();
    }

    //시간별 인원수 자료
    @Transactional
    public List<String> gettime_value(Long gymId) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            System.out.println(i);
            results.add(inputRepository.sumcount(String.valueOf(i),gymId,dayofweek()).toString());
        }

        return results;
    }


}
