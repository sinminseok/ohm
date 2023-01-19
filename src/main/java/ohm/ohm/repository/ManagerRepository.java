package ohm.ohm.repository;

import ohm.ohm.entity.Admin;
import ohm.ohm.entity.Manager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Optional<Manager> findByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<Manager> findOneWithAuthoritiesByName(String name);


}
