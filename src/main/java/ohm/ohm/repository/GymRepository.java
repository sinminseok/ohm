package ohm.ohm.repository;

import ohm.ohm.entity.Gym;
import ohm.ohm.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GymRepository extends JpaRepository<Gym,Long> {
    List<Gym> findByNameContaining(String name);

    //Modifying + Query를 통해 날린 쿼리는 영속성 컨텍스트를 통과하지 않고 DB에 바로 날려 기존 영속성 컨텍스트에 다시 조회하면 변경되지 않은 데이터가 조회됨 ㅇㅇ
    //이를 해결하기 위해  Modifying 속성 clearAutomatically를 true로 지정해주면 됨
    @Modifying(clearAutomatically = true)
    @Query("update Gym g set g.current_count = g.current_count + 1 where g.id = :id")
    int increase_count(@Param("id")Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Gym g set g.current_count = g.current_count - 1 where g.id = :id")
    int decrease_count(@Param("id")Long id);

}
