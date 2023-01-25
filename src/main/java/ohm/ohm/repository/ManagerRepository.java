package ohm.ohm.repository;
import ohm.ohm.entity.Manager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {


    @EntityGraph(attributePaths = "authorities")
    Optional<Manager> findOneWithAuthoritiesByName(String name);


    @Modifying(clearAutomatically = true)
    @Query(value = "update Manager m set m.gym_id = :gym_id where m.manager_id =:manager_id",nativeQuery = true)
    void registerByGymId(@Param("manager_id")Long manager_id,@Param("gym_id")Long gym_id);


}
