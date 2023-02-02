package ohm.ohm.repository;

import ohm.ohm.entity.Gym.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym,Long> {

    List<Gym> findByNameContaining(String name);

    @Query("select g from Gym g left join fetch g.imgs")
    List<Gym> findAllFetchJoin();

    @Modifying(clearAutomatically = true)
    @Query("update Gym g set g.current_count = g.current_count + 1 where g.id = :id")
    int increase_count(@Param("id")Long id);



    @Modifying(clearAutomatically = true)
    @Query("update Gym g set g.current_count = g.current_count - 1 where g.id = :id")
    int decrease_count(@Param("id")Long id);

}
