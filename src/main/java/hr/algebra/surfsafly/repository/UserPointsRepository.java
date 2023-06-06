package hr.algebra.surfsafly.repository;

import hr.algebra.surfsafly.model.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPointsRepository extends JpaRepository<UserPoints, Long> {
    Optional<UserPoints> findByUserId(Long userId);

    @Query("select u from UserPoints u order by u.score")
    List<UserPoints> findAllAndOrderByScore();
}
