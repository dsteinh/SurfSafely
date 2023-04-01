package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.dto.ApiResponse;
import hr.algebra.surfsafly.dto.UserDto;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.security.JwtGenerator;
import hr.algebra.surfsafly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    public static final String ALREADY_EXISTS_ERROR = "username already exists";
    private final UserService userService;
    private final JwtGenerator jwtGenerator;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserDto user) {
        if (userService.getByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>(ApiResponse.error(user,ALREADY_EXISTS_ERROR), HttpStatus.CONFLICT);
        }
        try {
            User u = User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();
            userService.saveUser(u);
            return new ResponseEntity<>(ApiResponse.ok(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error(user, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserDto user) {
        try {
            if (Objects.isNull(user.getUsername()) || Objects.isNull(user.getPassword())) {
                throw new UserNotFoundException("Username or Password is Empty");
            }
            User userData = userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
            if (Objects.isNull(userData)) {
                throw new UserNotFoundException("Username or Password is Invalid");
            }
            return new ResponseEntity<>(ApiResponse.ok(jwtGenerator.generateToken(
                    User.builder()
                            .username(userData.getUsername())
                            .password(userData.getPassword())
                            .firstName(userData.getFirstName())
                            .lastName(userData.getLastName())
                            .email(userData.getEmail())
                            .build())), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(ApiResponse.error(user, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
