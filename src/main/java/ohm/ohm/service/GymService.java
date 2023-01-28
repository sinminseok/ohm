package ohm.ohm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto;
import ohm.ohm.dto.GymImgDto;
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
                .introduce(gymDto.getIntroduce())
                .build();

        Gym save = gymRepository.save(gym);


        List<GymImg> gymImgList = fileHandler.parseFileInfo(save,files);


        if(!gymImgList.isEmpty()){
            for(GymImg gymImg : gymImgList){
                //파일을 DB에 저장
                gymImgRepository.save(gymImg);
//                gym.addImg(gymImgRepository.save(gymImg));
            }
        }

        return save.getId();

    }



    //모든 GYM 조회
    public List<GymDto> findall(){
        List<Gym> gyms = gymRepository.findAll();
        List<GymDto> gymDtos = new ArrayList<GymDto>();
        for(Gym gym : gyms){
            gymDtos.add(appConfig.modelMapper().map(gym,GymDto.class));
        }
        return gymDtos;
    }

    //GYM 이름으로 조회 - 클라이언트가사용
    public List<GymDto> findByName(String name){
        List<Gym> gyms = gymRepository.findByNameContaining(name);
        List<GymDto> gymDtos =  new ArrayList<GymDto>();
        for(Gym element : gyms){
            gymDtos.add(appConfig.modelMapper().map(element,GymDto.class));
        }

        return gymDtos;
    }



    //GYM ID로 조회
    public GymDto findById(Long id) throws Exception {
        Optional<Gym> gym = gymRepository.findById(id);
        if(gym.isPresent()){
            return appConfig.modelMapper().map(gym.get(),GymDto.class);
        }else{
            throw new Exception();
        }
    }


    //현재 GYM 인원수 조회
    public int current_count(Long id) throws Exception {
        GymDto gymdto = findById(id);
        return gymdto.getCurrent_count();
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
