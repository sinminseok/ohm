package ohm.ohm.repository.manager;
import ohm.ohm.entity.Gym.Gym;
import ohm.ohm.entity.Manager.Manager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {


    @EntityGraph(attributePaths = "authorities")
    Optional<Manager> findOneWithAuthoritiesByName(String name);

//@Query(value = "select * from MANAGER inner join MANAGER.GYM_ID where MANAGER.MANAGER_ID = :manager_id",nativeQuery = true)
    @Query("select m from Manager m left join fetch m.gym where m.id = :manager_id")
    Manager findManagerFetchJoinGym(@Param("manager_id")Long manager_id);

    @EntityGraph(attributePaths = "gym")
    Optional<Manager> findOneWithGymById(Long id);


    @Modifying(clearAutomatically = true)
    @Query(value = "update Manager m set m.gym_id = :gym_id where m.manager_id =:manager_id",nativeQuery = true)
    void registerByGymId(@Param("manager_id")Long manager_id,@Param("gym_id")Long gym_id);



    @Query(value = "select * from Manager where Manager.gym_id = :gym_id",nativeQuery = true)
    List<Optional<Manager>> findall_byGymId(@Param("gym_id")Long gym_id);


}
