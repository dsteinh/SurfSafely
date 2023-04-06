package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.converter.UserConverter;
import hr.algebra.surfsafly.dto.ApiResponse;
import hr.algebra.surfsafly.dto.UserDto;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.security.JwtUtils;
import hr.algebra.surfsafly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;
import javax.naming.AuthenticationException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthenticationController {
    public static final String ALREADY_EXISTS_ERROR = "username already exists";
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final UserConverter userConverter;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserDto userDto) {
        if (userService.getByUsername(userDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(ApiResponse.error(userDto, ALREADY_EXISTS_ERROR), HttpStatus.CONFLICT);
        }
        try {
            User user = userConverter.convert(userDto);
            userService.saveUser(user);
            return new ResponseEntity<>(ApiResponse.ok(userDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error(userDto, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserDto userDto) {
        try {
            if (Objects.isNull(userDto.getUsername()) || Objects.isNull(userDto.getPassword())) {
                throw new UserNotFoundException("Username or Password is Empty");
            }
            Optional<User> userData = userService.getUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
            if (Objects.isNull(userData)) {
                throw new UserNotFoundException("Username or Password is Invalid");
            }
            return new ResponseEntity<>(ApiResponse.ok(jwtUtils.generateToken(
                    userConverter.convert(userDto))), HttpStatus.OK);
        } catch (UserNotFoundException | AuthenticationException | RoleNotFoundException e) {
            return new ResponseEntity<>(ApiResponse.builder().error(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        }
    }
}
