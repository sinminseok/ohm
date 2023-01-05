package ohm.ohm.repository;

import ohm.ohm.entity.Gym;
import ohm.ohm.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GymRepository extends JpaRepository<Gym,Long> {
    List<Gym> findByNameContaining(String name);


}
