package ohm.ohm.service;

import lombok.RequiredArgsConstructor;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.TrainerDto;
import ohm.ohm.entity.Authority;
import ohm.ohm.entity.Gym;
import ohm.ohm.entity.Trainer;
import ohm.ohm.repository.GymRepository;
import ohm.ohm.repository.TrainerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final GymRepository gymRepository;
    private final AppConfig appConfig;
    private final PasswordEncoder passwordEncoder;


    //Manger가 등록 - 가입코드 부여
   @Transactional
   public TrainerDto signup(TrainerDto trainerDto,Long gymId){
       if(trainerRepository.findOneWithAuthoritiesByName(trainerDto.getName()).orElse(null) != null){
           throw new RuntimeException("이미 가입되어 있는 트레이너입니다.");
       }

       Optional<Gym> gym = gymRepository.findById(gymId);

       Authority authority = Authority.builder()
               .authorityName("ROLE_TRAINER")
               .build();

       Trainer trainer = new Trainer(trainerDto.getName(),passwordEncoder.encode(trainerDto.getPassword()),trainerDto.getSex(),gym.get());
       Trainer save = trainerRepository.save(trainer);
       return appConfig.modelMapper().map(save,TrainerDto.class);
   }




    @Transactional
    public Long save(TrainerDto trainerDto) {
        Trainer trainer = appConfig.modelMapper().map(trainerDto, Trainer.class);
        return trainerRepository.save(trainer).getId();
    }


    //헬스장에 등록된 모든 트레이너 조회
    public List<TrainerDto> findall(Long id) {
        Optional<Gym> gym = gymRepository.findById(id);
        List<Trainer> trainers = gym.get().getTrainers();
        List<TrainerDto> trainerDtos = new ArrayList<>();
        for (Trainer element : trainers) {
            trainerDtos.add(appConfig.modelMapper().map(element, TrainerDto.class));
        }

        return trainerDtos;
    }

    //트레이너 ID로 해당 트레이너 조회
    public TrainerDto findById(Long id){
        Optional<Trainer> trainer = trainerRepository.findById(id);
        TrainerDto trainerDto = appConfig.modelMapper().map(trainer, TrainerDto.class);
        return trainerDto;
    }

    @Transactional
    public Optional<Trainer> update(TrainerDto updateDto){
        Trainer update = appConfig.modelMapper().map(updateDto, Trainer.class);
        Optional<Trainer> byId = trainerRepository.findById(update.getId());
        byId.get().update(updateDto);
        return byId;
    }
}
