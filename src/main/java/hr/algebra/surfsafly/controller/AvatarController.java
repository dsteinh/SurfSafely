package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.Avatar;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.model.UserAvatar;
import hr.algebra.surfsafly.model.UserPoints;
import hr.algebra.surfsafly.repository.AvatarRepository;
import hr.algebra.surfsafly.repository.UserAvatarRepository;
import hr.algebra.surfsafly.repository.UserPointsRepository;
import hr.algebra.surfsafly.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/avatar")
@Log4j2
public class AvatarController {

    private final CurrentUserService currentUserService;
    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;
    private final UserPointsRepository userPointsRepository;


    @GetMapping("/currentUser")
    public ResponseEntity<ApiResponseDto> getCurrentUserMainAvatar() throws UserNotFoundException {
        User currentUser = currentUserService.getCurrentUser();
        UserAvatar userAvatar = userAvatarRepository.findByUserIdAndIsProfilePictureTrue(currentUser.getId()).orElseThrow();
        Avatar avatar = avatarRepository.findById(userAvatar.getAvatarId()).orElseThrow();

        return ResponseEntity.ok(ApiResponseDto.ok(avatar));
    }

    @GetMapping("/currentUser/all")
    public ResponseEntity<ApiResponseDto> getCurrentUserAvatars() throws UserNotFoundException {
        User currentUser = currentUserService.getCurrentUser();
        List<UserAvatar> userAvatars = userAvatarRepository.findAllByUserId(currentUser.getId());
        List<Avatar> avatars = avatarRepository.findAllById(userAvatars.stream().map(UserAvatar::getAvatarId).toList());

        return ResponseEntity.ok(ApiResponseDto.ok(avatars));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto> getAll() {
        List<Avatar> avatars = avatarRepository.findAll();
        return ResponseEntity.ok(ApiResponseDto.ok(avatars));
    }

    @PostMapping("/makeMain/{avatarId}")
    public ResponseEntity<ApiResponseDto> makeMain(@PathVariable Long avatarId) throws UserNotFoundException {
        Long userId = currentUserService.getCurrentUser().getId();
        List<UserAvatar> allByAvatarId = userAvatarRepository.findAllByUserId(userId);
                allByAvatarId.forEach(userAvatar -> userAvatar.setIsProfilePicture(false));
        userAvatarRepository.saveAll(allByAvatarId);

        UserAvatar byAvatarId = userAvatarRepository.findByAvatarIdAndUserId(avatarId, userId);
        byAvatarId.setIsProfilePicture(true);
        userAvatarRepository.save(byAvatarId);
        return ResponseEntity.ok(ApiResponseDto.ok("new avatar is set"));
    }

    @PostMapping("/buy/{avatarId}")
    public ResponseEntity<ApiResponseDto> buy(@PathVariable Long avatarId) throws UserNotFoundException {
        Avatar avatar = avatarRepository.findById(avatarId).orElseThrow();
        UserPoints userPoints = userPointsRepository.findByUserId(currentUserService.getCurrentUser().getId()).orElseThrow();
        if (userPoints.getMoney() < avatar.getPrice()) {
            return ResponseEntity.ok(ApiResponseDto.error("","not enough points"));
        }
        Long currentMoney = userPoints.getMoney();
        currentMoney -= avatar.getPrice();
        userPoints.setMoney(currentMoney);
        userPointsRepository.save(userPoints);
        userAvatarRepository.save(UserAvatar.builder()
                        .userId(currentUserService.getCurrentUser().getId())
                        .avatarId(avatar.getId())
                        .isProfilePicture(false).build());

        return ResponseEntity.ok(ApiResponseDto.ok("current money: " + userPoints.getMoney()));
    }
}
