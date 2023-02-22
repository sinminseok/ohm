package ohm.ohm.repository.gym;
import ohm.ohm.entity.Gym.GymPrice;
import ohm.ohm.entity.Gym.GymTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GymPriceRepository extends JpaRepository<GymPrice,Long> {

    @Query("select gp from GymPrice gp where gp.gym.id = :gymId")
    List<GymPrice> findPriceByGymId(@Param("gymId") Long gymId);

}
