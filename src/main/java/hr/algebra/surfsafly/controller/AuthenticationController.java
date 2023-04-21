package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.converter.UserConverter;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.dto.ChangePasswordDto;
import hr.algebra.surfsafly.dto.UserDto;
import hr.algebra.surfsafly.exception.PasswordMismatchException;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.JwtBlacklistData;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.security.JwtUtils;
import hr.algebra.surfsafly.service.CurrentUserService;
import hr.algebra.surfsafly.service.JwtBlacklistService;
import hr.algebra.surfsafly.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.naming.AuthenticationException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@Log4j2
public class AuthenticationController {
    public static final String ALREADY_EXISTS_ERROR = "username already exists";
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final UserConverter userConverter;
    private final JwtBlacklistService jwtBlacklistService;
    private final CurrentUserService currentUserService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> register(@RequestBody UserDto userDto) {
        if (userService.getByUsername(userDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(ApiResponseDto.error(userDto, ALREADY_EXISTS_ERROR), HttpStatus.CONFLICT);
        }
        try {
            User user = userConverter.convert(userDto);
            userService.saveUser(user);
            return new ResponseEntity<>(ApiResponseDto.ok(userDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponseDto.error(userDto, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody UserDto userDto) {
        try {
            if (Objects.isNull(userDto.getUsername()) || Objects.isNull(userDto.getPassword())) {
                throw new UserNotFoundException("Username or Password is Empty");
            }
            Optional<User> userData = userService.getUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
            if (userData.isEmpty()) {
                throw new UserNotFoundException("Username or Password is Invalid");
            }
            return new ResponseEntity<>(ApiResponseDto.ok(jwtUtils.generateToken(
                    userConverter.convert(userDto))), HttpStatus.OK);
        } catch (UserNotFoundException | AuthenticationException | RoleNotFoundException e) {
            return new ResponseEntity<>(ApiResponseDto.builder().error(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponseDto> blacklistToken(@RequestHeader(name = "Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }
        JwtBlacklistData jwtBlacklistData = JwtBlacklistData.builder()
                .token(token).build();
        jwtBlacklistService.save(jwtBlacklistData);
        return ResponseEntity.ok(ApiResponseDto.ok("token added to blacklist"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponseDto> changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws UserNotFoundException, PasswordMismatchException {
        User currentUser = currentUserService.getCurrentUser();
        userService.changePassword(changePasswordDto, currentUser);
        return ResponseEntity.ok(ApiResponseDto.ok("password was successfully changed"));
    }
}
