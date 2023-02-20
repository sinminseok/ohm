package ohm.ohm.repository.post;

import ohm.ohm.entity.Post.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

//    @Query("select p from Post p where p.gym.id = :id")
//    List<Post> findBy_gymId(@Param("id") Long id);

    @Query("select p from Post p where p.gym.id = :id")
    Slice<Post> findBy_gymId(@Param("id") Long id, Pageable pageable);

}
