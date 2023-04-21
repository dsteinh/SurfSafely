package hr.algebra.surfsafly.service;

import hr.algebra.surfsafly.dto.ChangePasswordDto;
import hr.algebra.surfsafly.exception.PasswordMismatchException;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public interface UserService {
    void saveUser(User user);

    Optional<User> getUserByUsernameAndPassword(String username, String password) throws UserNotFoundException, AuthenticationException;

    Optional<User> getByUsername(String username);

    void changePassword(ChangePasswordDto changePasswordDto, User user) throws PasswordMismatchException;
}
