package ohm.ohm.repository;

import ohm.ohm.entity.Admin;
import ohm.ohm.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Optional<Manager> findByEmail(String email);
}
