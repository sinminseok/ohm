package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto;
import ohm.ohm.dto.GymImgDto;
import ohm.ohm.dto.responseDto.GymImgResponseDto;
import ohm.ohm.dto.responseDto.GymResponseDto;
import ohm.ohm.entity.Gym;
import ohm.ohm.entity.GymImg;
import ohm.ohm.repository.GymImgRepository;
import ohm.ohm.repository.GymRepository;
import ohm.ohm.utils.FileHandlerUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GymService {

    private final GymRepository gymRepository;
    private final GymImgRepository gymImgRepository;
    private final AppConfig appConfig;
    private final FileHandlerUtils fileHandler;
    //헬스장 생성 -- ROLE이 ROLE_MANAGER인 Manager만 사용가능


    @Transactional
    public Long save(GymDto gymDto, List<MultipartFile> files) throws Exception {

        //img save
        Gym gym = Gym.builder()
                .address(gymDto.getAddress())
                .code(gymDto.getCode())
                .count(gymDto.getCount())
                .name(gymDto.getName())
                .oneline_introduce(gymDto.getOneline_introduce())
                .holiday(gymDto.getHoliday())
                .trainer_count(gymDto.getTrainer_count())
                .weekday_time(gymDto.getWeekday_time())
                .weekend_time(gymDto.getWeekend_time())
                .introduce(gymDto.getIntroduce())
                .build();

        Gym save = gymRepository.save(gym);

        List<GymImg> gymImgList = fileHandler.gymimg_parseFileInfo(save, files);


        if (!gymImgList.isEmpty()) {
            for (GymImg gymImg : gymImgList) {
                //파일을 DB에 저장
                gymImgRepository.save(gymImg);
            }
        }

        return save.getId();

    }


    //모든 GYM 조회 App에서 List형식으로 조회
    public List<GymResponseDto> findall() {
        List<Gym> gyms = gymRepository.findAllFetchJoin();

        List<GymResponseDto> gymDtos = new ArrayList<GymResponseDto>();

        for (Gym gym : gyms) {
            List<GymImgResponseDto> gymImgDtos = new ArrayList<GymImgResponseDto>();
            for(GymImg gymImg :gym.getImgs()){
                gymImgDtos.add(appConfig.modelMapper().map(gymImg,GymImgResponseDto.class));
            }

            GymResponseDto gymResponseDto = GymResponseDto.builder()
                    .address(gym.getAddress())
                    .name(gym.getName())
                    .introduce(gym.getIntroduce())
                    .oneline_introduce(gym.getOneline_introduce())
                    .imgs(gymImgDtos)
                    .count(gym.getCount()).build();
            gymDtos.add(gymResponseDto);
        }
        return gymDtos;
    }


    //GYM 이름으로 조회 - 클라이언트가사용
    public List<GymResponseDto> findByName(String name) {
        List<Gym> gyms = gymRepository.findByNameContaining(name);
        List<GymResponseDto> gymDtos = new ArrayList<GymResponseDto>();
        for (Gym element : gyms) {
            gymDtos.add(appConfig.modelMapper().map(element, GymResponseDto.class));
        }

        return gymDtos;
    }


    //GYM ID로 조회
    public GymResponseDto findById(Long id) throws Exception {
        Optional<Gym> gym = gymRepository.findById(id);
        if (gym.isPresent()) {
            return appConfig.modelMapper().map(gym.get(), GymResponseDto.class);
        } else {
            throw new Exception();
        }
    }

    public GymDto findById_count(Long id) throws Exception {
        Optional<Gym> byId = gymRepository.findById(id);
        if (byId.isPresent()) {
            return appConfig.modelMapper().map(byId.get(), GymDto.class);
        } else {
            throw new Exception();
        }
    }


    //현재 GYM 인원수 조회
    public int current_count(Long id) throws Exception {
        Optional<Gym> byId = gymRepository.findById(id);
        return byId.get().getCurrent_count();
    }


    //현재 GYM 인원수 증가(1증가)
    @Transactional
    public void increase_count(Long id) throws Exception {
        gymRepository.increase_count(id);
    }

    //현재 GYM 인원수 감소(1감소)
    @Transactional
    public void decrease_count(Long id) throws Exception {
        gymRepository.decrease_count(id);
    }


}
