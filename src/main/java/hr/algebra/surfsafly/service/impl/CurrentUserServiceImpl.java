package hr.algebra.surfsafly.service.impl;

import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.service.CurrentUserService;
import hr.algebra.surfsafly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private final UserService userService;

    @Override
    public User getCurrentUser() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByUsername(authentication.getName()).orElseThrow(() -> new UserNotFoundException("user not found"));
    }
}
