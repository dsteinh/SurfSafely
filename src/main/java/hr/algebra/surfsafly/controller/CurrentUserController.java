package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.dto.ChangeUserInformationDto;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.service.CurrentUserService;
import hr.algebra.surfsafly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/current-user/")
@RequiredArgsConstructor
public class CurrentUserController {

    private final CurrentUserService currentUserService;
    private final UserService userService;

    @PostMapping("update-personal-information")
    public ResponseEntity<ApiResponseDto> changePersonalData(@RequestBody ChangeUserInformationDto dto) throws UserNotFoundException {
        User currentUser = currentUserService.getCurrentUser();
        mapPersonalDataDtoToUser(dto, currentUser);
        userService.save(currentUser);
        return ResponseEntity.ok(ApiResponseDto.ok(dto, "user data updated"));
    }

    @DeleteMapping("delete-account")
    public ResponseEntity<ApiResponseDto> deleteAccount(@RequestHeader(name = "Authorization") String token) throws UserNotFoundException {
        User currentUser = currentUserService.getCurrentUser();
        userService.anonymizeUser(currentUser, token);
        return ResponseEntity.ok(ApiResponseDto.ok(currentUser, "user account deleted"));
    }
    private void mapPersonalDataDtoToUser(ChangeUserInformationDto dto, User currentUser) {
        currentUser.setEmail(Objects.nonNull(dto.getNewEmail()) ? dto.getNewEmail() : currentUser.getEmail());
        currentUser.setFirstName(Objects.nonNull(dto.getNewFistName()) ? dto.getNewFistName() : currentUser.getFirstName());
        currentUser.setLastName(Objects.nonNull(dto.getNewLastName()) ? dto.getNewLastName() : currentUser.getLastName());
    }
}
