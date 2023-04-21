package hr.algebra.surfsafly.service.impl;

import hr.algebra.surfsafly.dto.ChangePasswordDto;
import hr.algebra.surfsafly.exception.PasswordMismatchException;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.JwtBlacklistData;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.repository.UserRepository;
import hr.algebra.surfsafly.service.JwtBlacklistService;
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
    public static final String CURRENT_PASSWORD_ERROR = "wrong current password";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtBlacklistService jwtBlacklistService;

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

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto, User user) throws PasswordMismatchException {
        if (encoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(changePasswordDto.getNewPassword());
            saveUser(user);
            return;
        }
        throw new PasswordMismatchException(CURRENT_PASSWORD_ERROR);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void anonymizeUser(User user, String token) {
        userRepository.anonymizeUser(user);
        jwtBlacklistService.save(JwtBlacklistData.builder().token(token).build());
    }
}
