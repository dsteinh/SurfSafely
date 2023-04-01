package hr.algebra.surfsafly.repository;

import hr.algebra.surfsafly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
    Optional<User> findUserByUsername(String username);
}
