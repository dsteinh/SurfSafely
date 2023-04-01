package hr.algebra.surfsafly.service;

import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    void saveUser(User user);

    User getUserByUsernameAndPassword(String username, String password) throws UserNotFoundException;

    Optional<User> getByUsername(String username);
}
