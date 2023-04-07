package hr.algebra.surfsafly.service.impl;

import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.repository.UserRepository;
import hr.algebra.surfsafly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USERNAME_OR_PASSWORD_ERROR = "Invalid username or password";
    public static final String USER_NOT_FOUND_ERROR = "User with username %s doesn't exist";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void saveUser(User user) {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsernameAndPassword(String username, String password) throws UserNotFoundException, AuthenticationException {

        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format(USER_NOT_FOUND_ERROR, username));
        }
        if (encoder.matches(password, user.get().getPassword())) {
            return user;
        } else {
            throw new AuthenticationException(USERNAME_OR_PASSWORD_ERROR);
        }
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

}
