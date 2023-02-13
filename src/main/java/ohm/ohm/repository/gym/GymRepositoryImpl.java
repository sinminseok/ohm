package ohm.ohm.repository.gym;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import ohm.ohm.entity.Gym.Gym;

import static ohm.ohm.entity.Gym.QGym.gym;
import static ohm.ohm.entity.Gym.QGymImg.gymImg;

import java.util.List;

@RequiredArgsConstructor
public class GymRepositoryImpl implements GymRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;


    @Override
    public List<Gym> findAllGymList() {
        return jpqlQueryFactory.selectFrom(gym)
                .leftJoin(gym.imgs, gymImg)
                .fetch();
    }
}
