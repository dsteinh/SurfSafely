package hr.algebra.surfsafly.repository;

import hr.algebra.surfsafly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);

    Optional<User> findUserByUsername(String username);


    @Query("""
            UPDATE User
            SET
              firstName = 'Anonymous',
              lastName = 'Anonymous',
              username = CONCAT('Anonymous_', :#{#user.id.toString()}),
              password = 'Anonymous',
              email = 'Anonymous@mail.com'
            WHERE id = :#{#user.id}""")
    @Modifying
    void anonymizeUser(@Param("user") User user);

}
