package ohm.ohm.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.GymDto.GymDto;
import ohm.ohm.dto.GymDto.GymPriceDto;
import ohm.ohm.dto.GymDto.GymTimeDto;
import ohm.ohm.dto.requestDto.GymRequestDto;
import ohm.ohm.dto.responseDto.GymImgResponseDto;
import ohm.ohm.dto.responseDto.GymResponseDto;
import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Gym.GymImg;
import ohm.ohm.entity.Gym.GymPrice;
import ohm.ohm.entity.Gym.GymTime;
import ohm.ohm.repository.gym.GymImgRepository;
import ohm.ohm.repository.gym.GymPriceRepository;
import ohm.ohm.repository.gym.GymRepository;
import ohm.ohm.repository.gym.GymTimeRepository;
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
    private final GymTimeRepository gymTimeRepository;
    private final GymPriceRepository gymPriceRepository;
    private final InputService inputService;



    //헬스장 생성 -- ROLE이 ROLE_MANAGER인 Manager만 사용가능
    @Transactional
    public Long save(GymRequestDto gymDto) throws Exception {

        //img save
        Gym gym = Gym.builder()
                .address(gymDto.getAddress())
                .code(gymDto.getCode())
                .count(gymDto.getCount())
                .name(gymDto.getName())
                .area(gymDto.getArea())
                .oneline_introduce(gymDto.getOneline_introduce())
                .trainer_count(gymDto.getTrainer_count())
                .introduce(gymDto.getIntroduce())
                .build();

        Gym save = gymRepository.save(gym);
        return save.getId();

    }

    @Transactional
    public Long save_img(Long gymId, List<MultipartFile> files) throws Exception {

        Optional<Gym> gym = gymRepository.findById(gymId);


        List<GymImg> gymImgList = fileHandler.gymimg_parseFileInfo(gym.get(), files);


        if (!gymImgList.isEmpty()) {
            for (GymImg gymImg : gymImgList) {
                //파일을 DB에 저장
                gymImgRepository.save(gymImg);
            }
        }

        return gym.get().getId();

    }


    //모든 GYM 조회 App에서 List형식으로 조회
    public List<GymResponseDto> findall() {
        List<Gym> gyms = gymRepository.findAllGymList();

        List<GymResponseDto> gymDtos = new ArrayList<GymResponseDto>();

        for (Gym gym : gyms) {
            List<GymImgResponseDto> gymImgDtos = new ArrayList<GymImgResponseDto>();
            for(GymImg gymImg :gym.getImgs()){
                gymImgDtos.add(appConfig.modelMapper().map(gymImg,GymImgResponseDto.class));
            }

            GymResponseDto gymResponseDto = GymResponseDto.builder()
                    .address(gym.getAddress())
                    .id(gym.getId())
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

        for (Gym gym : gyms) {
            List<GymImgResponseDto> gymImgDtos = new ArrayList<GymImgResponseDto>();
            for(GymImg gymImg :gym.getImgs()){
                gymImgDtos.add(appConfig.modelMapper().map(gymImg,GymImgResponseDto.class));
            }

            GymResponseDto gymResponseDto = GymResponseDto.builder()
                    .address(gym.getAddress())
                    .name(gym.getName())
                    .id(gym.getId())
                    .introduce(gym.getIntroduce())
                    .oneline_introduce(gym.getOneline_introduce())
                    .imgs(gymImgDtos)
                    .count(gym.getCount()).build();
            gymDtos.add(gymResponseDto);
        }
        return gymDtos;
    }


    //GYM ID로 조회
    public GymResponseDto findById(Long id) throws Exception {
        Gym gym = gymRepository.findGymFetchJoin(id);
        List<GymImgResponseDto> gymImgDtos = new ArrayList<GymImgResponseDto>();
        for(GymImg gymImg :gym.getImgs()){
            gymImgDtos.add(appConfig.modelMapper().map(gymImg,GymImgResponseDto.class));
        }

        GymResponseDto gymResponseDto = GymResponseDto.builder()
                .address(gym.getAddress())
                .id(gym.getId())
                .name(gym.getName())
                .introduce(gym.getIntroduce())
                .oneline_introduce(gym.getOneline_introduce())
                .imgs(gymImgDtos)
                .count(gym.getCount()).build();

        return gymResponseDto;

    }



    //Gym Id로 count를 증가시킬 gym 리턴
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
        int count = gymRepository.increase_count(id);

    }

    //현재 GYM 인원수 감소(1감소)
    @Transactional
    public void decrease_count(Long id) throws Exception {
        int count = gymRepository.decrease_count(id);
        inputService.insert_data(count,id);
    }

    @Transactional
    public Long register_price(Long gymId, GymPriceDto gymPriceDto){

        Optional<Gym> byId = gymRepository.findById(gymId);

        GymPrice gymPrice_builder = GymPrice.builder()
                .price(gymPriceDto.getPrice())
                .during(gymPriceDto.getDuring())
                .gym(byId.get())
                .build();

        GymPrice save = gymPriceRepository.save(gymPrice_builder);
        return save.getId();
    }

    @Transactional
    public Long register_time(Long gymId, GymTimeDto gymTimeDto){

        Optional<Gym> byId = gymRepository.findById(gymId);

        GymTime gymTime = GymTime.builder()
                .gym(byId.get())
                .HOLIDAY(gymTimeDto.getHoliday())
                .WEEKDAY(gymTimeDto.getWeekday())
                .SATURDAY(gymTimeDto.getSaturday())
                .SUNDAY(gymTimeDto.getSunday())
                .CLOSEDDAYS(gymTimeDto.getCloseddays())
                .build();



        GymTime save = gymTimeRepository.save(gymTime);

        //변경감지
        byId.get().register_time(save);


        return save.getId();
    }


    public Long check_code(int code) throws Exception{
        Gym gym = gymRepository.find_code(code);
        return gym.getId();
    }

    @Transactional
    public Optional<Gym> update_gym(GymDto gymDto){
        Optional<Gym> byId = gymRepository.findById(gymDto.getId());
        byId.get().update(appConfig.modelMapper().map(gymDto,Gym.class));
        return byId;
    }


}
