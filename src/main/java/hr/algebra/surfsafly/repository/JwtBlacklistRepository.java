package hr.algebra.surfsafly.repository;

import hr.algebra.surfsafly.model.JwtBlacklistData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklistData, Long> {
}
