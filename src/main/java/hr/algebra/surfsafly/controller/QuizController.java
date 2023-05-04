package hr.algebra.surfsafly.controller;


import hr.algebra.surfsafly.converter.QuizConverter;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.dto.QuizDto;
import hr.algebra.surfsafly.model.Quiz;
import hr.algebra.surfsafly.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/quiz")
@Log4j2
public class QuizController {
    private final QuizService quizService;
    private final QuizConverter quizConverter;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> saveQuiz(@RequestBody QuizDto quizDto) {
        Quiz quiz = quizConverter.convert(quizDto);
        log.warn(quiz);
        return ResponseEntity.ok(ApiResponseDto.ok(quizService.create(quiz)));
    }
    //dodati rud operacije
}
