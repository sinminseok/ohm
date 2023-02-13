package ohm.ohm.repository.manager;
import ohm.ohm.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code,Long>{

     @Query("select c from Code c where c.code = :code")
     Optional<Code> findCode(@Param("code") String code);

}
