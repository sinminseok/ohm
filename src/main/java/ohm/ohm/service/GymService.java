package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto;
import ohm.ohm.entity.Gym;
import ohm.ohm.repository.GymRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GymService {


    private final GymRepository gymRepository;
    private final AppConfig appConfig;

    //헬스장 생성 --manager가 사용
    @Transactional
    public Long save(GymDto gymDto){
        Gym gym = appConfig.modelMapper().map(gymDto, Gym.class);
        Gym save = gymRepository.save(gym);
        return save.getId();
    }


    //모든 헬스장 조회 - admin이 사용
    public List<GymDto> findall(){
        List<Gym> gyms = gymRepository.findAll();
        List<GymDto> gymDtos = new ArrayList<GymDto>();
        for(Gym gym : gyms){
            gymDtos.add(appConfig.modelMapper().map(gym,GymDto.class));
        }
        return gymDtos;
    }

    //이름으로 조회계 - 클라이언트가사용
    public List<GymDto> findByName(String name){
        List<Gym> gyms = gymRepository.findByNameContaining(name);
        List<GymDto> gymDtos =  new ArrayList<GymDto>();
        for(Gym element : gyms){
            gymDtos.add(appConfig.modelMapper().map(element,GymDto.class));
        }

        return gymDtos;
    }


    //Gym Id로 조회 (어플 디스크에 1차 조회후 Id로 매번조회) - 클라이언트가 사용
    public GymDto findById(Long id) throws Exception {
        Optional<Gym> gym = gymRepository.findById(id);
        if(gym.isPresent()){
            return appConfig.modelMapper().map(gym.get(),GymDto.class);
        }else{
            throw new Exception();
        }
    }


    //Gym Id로 조회
    public int current_count(Long id) throws Exception {
        GymDto gymdto = findById(id);
        return gymdto.getCurrent_count();
    }


    //현재 인원 추가 method
    @Transactional
    public void increase_count(Long id) throws Exception {
        gymRepository.increase_count(id);
    }

    //현재 인원 감소 method
    @Transactional
    public void decrease_count(Long id) throws Exception {
        gymRepository.decrease_count(id);
    }



}
