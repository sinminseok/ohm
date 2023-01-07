package ohm.ohm.service;

import lombok.RequiredArgsConstructor;
import ohm.ohm.config.AppConfig;
import ohm.ohm.dto.TrainerDto;
import ohm.ohm.entity.Gym;
import ohm.ohm.entity.Trainer;
import ohm.ohm.repository.GymRepository;
import ohm.ohm.repository.TrainerRepository;
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


    //trainer 등록 및 소개 - manager가 등록
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
