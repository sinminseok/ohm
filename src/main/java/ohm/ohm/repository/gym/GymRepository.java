package ohm.ohm.repository.gym;

import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.repository.gym.GymRepositoryCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym,Long>, GymRepositoryCustom {

    @EntityGraph(attributePaths = "imgs")
    List<Gym> findByNameContaining(String name);

//    @Query("select g from Gym g left join fetch g.imgs")
//    List<Gym> findAllFetchJoin();

    @Query("select g from Gym g left join fetch g.imgs where g.id = :id")
    Gym findGymFetchJoin(@Param("id")Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Gym g set g.current_count = g.current_count + 1 where g.id = :id")
    int increase_count(@Param("id")Long id);


    @Modifying(clearAutomatically = true)
    @Query("update Gym g set g.current_count = g.current_count - 1 where g.id = :id")
    int decrease_count(@Param("id")Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Gym g set g.current_count = 0 where g.id = :id")
    int reset_count(@Param("id")Long id);

    @Query("select g from Gym g where g.code = :code")
    Gym find_code(@Param("code")int code);


    @Query("select g from Gym g left join fetch g.gymTime where g.id = :id")
    Gym findTimeByGymId(@Param("id")Long id);

    @Query("select g from Gym g where g.code = :code")
    Gym checkCode(@Param("code") int code);
}
