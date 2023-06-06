package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.model.UserPoints;
import hr.algebra.surfsafly.repository.UserPointsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/leaderboard")
@Log4j2
public class LeaderboardController {

    private final UserPointsRepository userPointsRepository;

    @GetMapping()
    public ResponseEntity<ApiResponseDto> getLeaderboard() {
        List<UserPoints> userPointsList = userPointsRepository.findAllAndOrderByScore();
        return ResponseEntity.ok(ApiResponseDto.ok(userPointsList));
    }
}
