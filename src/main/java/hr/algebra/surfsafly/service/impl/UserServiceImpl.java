package hr.algebra.surfsafly.service.impl;

import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.repository.UserRepository;
import hr.algebra.surfsafly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("Invalid username and password");
        }
        return user;
    }

}
