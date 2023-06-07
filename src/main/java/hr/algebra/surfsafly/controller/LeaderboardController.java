package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.converter.UserConverter;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.dto.UserDto;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.model.UserPoints;
import hr.algebra.surfsafly.repository.UserPointsRepository;
import hr.algebra.surfsafly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/leaderboard")
@Log4j2
public class LeaderboardController {

    private final UserPointsRepository userPointsRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @GetMapping()
    public ResponseEntity<ApiResponseDto> getLeaderboard() {
        List<UserPoints> userPointsList = userPointsRepository.findAllAndOrderByScore();
        return ResponseEntity.ok(ApiResponseDto.ok(userPointsList));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponseDto> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        UserDto userDto = userConverter.convert(user);
        return ResponseEntity.ok(ApiResponseDto.ok(userDto));
    }
}
