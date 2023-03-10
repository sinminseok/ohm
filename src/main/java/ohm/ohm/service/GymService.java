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
import ohm.ohm.s3.AmazonS3ResourceStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GymService {


    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final GymRepository gymRepository;
    private final GymImgRepository gymImgRepository;
    private final AppConfig appConfig;
    private final GymTimeRepository gymTimeRepository;
    private final GymPriceRepository gymPriceRepository;


    @Transactional
    public void delete_price(List<Long> ids) throws Exception {
        for (Long id : ids) {
            gymPriceRepository.delete(gymPriceRepository.findById(id).get());
        }
        return;
    }


    @Transactional
    public void delete_img(List<Long> ids) throws Exception {

        for (Long id : ids) {
            GymImg gymImg = gymImgRepository.findById(id).get();
            amazonS3ResourceStorage.deleteObjectByKey(gymImg.getFilePath());
            gymImgRepository.delete(gymImg);
        }
    }


    //헬스장 생성 -- ROLE이 ROLE_MANAGER인 Manager만 사용가능
    @Transactional
    public Long save(GymRequestDto gymDto) throws Exception {

        // gymRepository.checkCode(gymDto.getCode());

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
        if (files == null) {

        } else {

            for (MultipartFile multipartFile : files) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter =
                        DateTimeFormatter.ofPattern("yyyyMMdd");
                String current_date = now.format(dateTimeFormatter);
                String uuid_string = UUID.randomUUID().toString();


                String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                //url,orignName

                // 파일 DTO 생성
                GymImg gymImg = GymImg.builder()
                        .gym(gym.get())
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(current_date + File.separator +uuid_string+ext)
                        .build();

                amazonS3ResourceStorage.upload(multipartFile, current_date,uuid_string+ext);
                gymImgRepository.save(gymImg);
            }

        }


        return gym.get().getId();

    }


    //모든 GYM 조회 App에서 List형식으로 조회
    public List<GymResponseDto> findall() {
        List<Gym> gyms = gymRepository.findAllFetchJoin();

        List<GymResponseDto> gymDtos = new ArrayList<GymResponseDto>();

        System.out.println(gyms.size());
        for (Gym gym : gyms) {
            List<GymImgResponseDto> gymImgDtos = new ArrayList<GymImgResponseDto>();
            for (GymImg gymImg : gym.getImgs()) {
                gymImgDtos.add(appConfig.modelMapper().map(gymImg, GymImgResponseDto.class));
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
        System.out.println(gymDtos.size());
        return gymDtos;
    }


    //GYM 이름으로 조회 - 클라이언트가사용
    public List<GymResponseDto> findByName(String name) {
        List<Gym> gyms = gymRepository.findByNameContaining(name);
        List<GymResponseDto> gymDtos = new ArrayList<GymResponseDto>();

        for (Gym gym : gyms) {
            List<GymImgResponseDto> gymImgDtos = new ArrayList<GymImgResponseDto>();
            for (GymImg gymImg : gym.getImgs()) {
                gymImgDtos.add(appConfig.modelMapper().map(gymImg, GymImgResponseDto.class));
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
        for (GymImg gymImg : gym.getImgs()) {
            gymImgDtos.add(appConfig.modelMapper().map(gymImg, GymImgResponseDto.class));
        }

        GymResponseDto gymResponseDto = GymResponseDto.builder()
                .address(gym.getAddress())
                .trainer_count(gym.getTrainer_count())
                .code(gym.getCode())
                .current_count(gym.getCurrent_count())
                .id(gym.getId())
                .name(gym.getName())
                .introduce(gym.getIntroduce())
                .oneline_introduce(gym.getOneline_introduce())
                .imgs(gymImgDtos)
                .count(gym.getCount()).build();

        return gymResponseDto;

    }


    public int findById_count(Long id) throws Exception {
        Optional<Gym> byId = gymRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get().getCurrent_count();
        } else {
            throw new Exception();
        }
    }


//    public CountResponseDto find_countresponse(Long gymId) throws Exception {
//        Optional<Gym> byId = gymRepository.findById(gymId);
//        String avgCount;
//        //현재인원
//        int current_count = byId.get().getCurrent_count();
//
//        //평균
//        Double dateavg = inputRepository.dateavg(inputService.dayofweek(), gymId);
//        System.out.println(dateavg);
//        System.out.println(current_count);
//        System.out.println("current_countcurrent_count");
//        if((double) current_count <= dateavg){
//            avgCount = "현재 헬스장은 원활합니다.";
//        }else{
//            avgCount = "현재 헬스장은 혼잡합니다";
//        }
//        CountResponseDto countResponseDto = CountResponseDto.builder()
//                .count(current_count)
//                .avgCount(avgCount)
//                .build();
//        if (byId.isPresent()) {
//            return countResponseDto;
//        } else {
//            throw new Exception();
//        }
//    }


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

    //현재 GYM 인원수 0으로 초기화
    @Transactional
    public void reset_count(Long id) throws Exception {
        int count = gymRepository.reset_count(id);
    }

    //현재 GYM 인원수 감소(1감소)
    @Transactional
    public void decrease_count(Long id) throws Exception {
        Optional<Gym> byId = gymRepository.findById(id);
        if (byId.get().getCurrent_count() == 0) {
            return;
        } else {
            int count = gymRepository.decrease_count(id);
        }


    }

    @Transactional
    public Long register_price(Long gymId, GymPriceDto gymPriceDto) {

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
    public Long register_time(Long gymId, GymTimeDto gymTimeDto) {

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

    public GymTimeDto get_time(Long gymId) {
        Gym timeByGymId = gymRepository.findTimeByGymId(gymId);
        GymTime gymTime = timeByGymId.getGymTime();
        return appConfig.modelMapper().map(gymTime, GymTimeDto.class);
    }

    public List<GymPriceDto> get_prices(Long gymId) {
        List<GymPrice> prices = gymPriceRepository.findPriceByGymId(gymId);
        List<GymPriceDto> priceDtos = new ArrayList<GymPriceDto>();
        for (GymPrice gymPrice : prices) {
            priceDtos.add(appConfig.modelMapper().map(gymPrice, GymPriceDto.class));
        }
        return priceDtos;
    }


    public Long check_code(int code) throws Exception {
        Gym gym = gymRepository.find_code(code);
        return gym.getId();
    }

    public boolean duplication_code(int code) throws Exception {
        Gym gym = gymRepository.checkCode(code);
        if (gym == null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Optional<Gym> update_gym(GymDto gymDto) {
        Optional<Gym> byId = gymRepository.findById(gymDto.getId());
        byId.get().update(appConfig.modelMapper().map(gymDto, Gym.class));
        return byId;
    }

    @Transactional
    public Optional<GymTime> update_time(Long gymId, GymTimeDto gymTimeDto) {

        Optional<GymTime> byId = gymTimeRepository.findById(gymTimeDto.getId());
        byId.get().update(appConfig.modelMapper().map(gymTimeDto, GymTime.class));

        return byId;
    }


}
