package hr.algebra.surfsafly.controller;


import hr.algebra.surfsafly.converter.QuizConverter;
import hr.algebra.surfsafly.dto.AnswerDto;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.dto.QuizDto;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.Quiz;
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
    private final QuizService quizService;
    private final QuizConverter quizConverter;

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(ApiResponseDto.ok(quiz));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto> getAllQuizzes() {
        List<Quiz> all = quizService.getAll();
        return ResponseEntity.ok(ApiResponseDto.ok(all));
    }

    @PostMapping("/solve/{quizId}")
    public ResponseEntity<ApiResponseDto> solveQuiz(@PathVariable Long quizId,
                                                    @RequestBody List<AnswerDto> answerDtos) {
        quizService.calculateResults(quizId,answerDtos);
        return null;
    }
}
