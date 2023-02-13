package ohm.ohm.repository.gym;

import ohm.ohm.entity.Gym.GymTime;
import ohm.ohm.entity.Manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymTimeRepository extends JpaRepository<GymTime,Long> {
}
