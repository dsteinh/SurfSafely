package hr.algebra.surfsafly.repository;

import hr.algebra.surfsafly.model.JwtBlacklistData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklistData, Long> {
    Optional<JwtBlacklistData> findByToken(String token);
    void deleteByToken(String token);
}
