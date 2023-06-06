package hr.algebra.surfsafly.controller;


import hr.algebra.surfsafly.converter.QuizConverter;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.dto.QuizDto;
import hr.algebra.surfsafly.dto.SolveAttemptDto;
import hr.algebra.surfsafly.dto.SolveAttemptResultDto;
import hr.algebra.surfsafly.exception.ElementNotFoundException;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.Quiz;
import hr.algebra.surfsafly.model.UserPoints;
import hr.algebra.surfsafly.repository.AnswerRepository;
import hr.algebra.surfsafly.repository.UserPointsRepository;
import hr.algebra.surfsafly.service.CurrentUserService;
import hr.algebra.surfsafly.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/quiz")
@Log4j2
public class QuizController {
    private final AnswerRepository answerRepository;
    private final QuizService quizService;
    private final QuizConverter quizConverter;
    private final UserPointsRepository userPointsRepository;
    private final CurrentUserService currentUserService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponseDto> saveQuiz(@RequestBody QuizDto quizDto) throws UserNotFoundException {
        Quiz quiz = quizConverter.convert(quizDto);
        QuizDto quizResponseDto = quizConverter.convert(quizService.create(quiz));
        return ResponseEntity.ok(ApiResponseDto.ok(quizResponseDto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponseDto> deleteQuiz(@PathVariable Long id) {
        quizService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.ok("quiz deleted"));
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponseDto> deleteAll() {
        quizService.deleteAll();
        return ResponseEntity.ok(ApiResponseDto.ok("all quizzes deleted"));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto> getAllQuizzesDto() {
        List<QuizDto> quizDtos = quizService.getAll().stream().map(quizConverter::convert).toList();
        return ResponseEntity.ok(ApiResponseDto.ok(quizDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> getQuizDtoById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        QuizDto quizDto = quizConverter.convert(quiz);
        return ResponseEntity.ok(ApiResponseDto.ok(quizDto));
    }

    @PostMapping("/solve")
    public ResponseEntity<ApiResponseDto> solveQuiz(@RequestBody SolveAttemptDto solveAttemptDto) throws ElementNotFoundException, UserNotFoundException {
        Double result = quizService.calculateResults(solveAttemptDto);
        Quiz quiz = answerRepository.findById(solveAttemptDto.getAnswerIds().get(0)).orElseThrow(() -> new ElementNotFoundException("element not found", "answer")).getQuestion().getQuiz();
        UserPoints userPoints = userPointsRepository.findByUserId(currentUserService.getCurrentUser().getId()).orElseThrow();
        userPointsRepository.save(
          UserPoints.builder()
                  .id(userPoints.getId())
                  .userId(userPoints.getUserId())
                  .money((long) (userPoints.getMoney()+(result*100)))
                  .score((long) (userPoints.getScore()+(result*100))).build());

        var response = SolveAttemptResultDto.builder()
                .quizId(quiz.getId())
                .correctnessPercentage(result).build();
        return ResponseEntity.ok(ApiResponseDto.ok(response));
    }
}
