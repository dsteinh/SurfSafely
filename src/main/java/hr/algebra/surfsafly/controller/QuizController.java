package hr.algebra.surfsafly.controller;


import hr.algebra.surfsafly.dto.QuizDto;
import hr.algebra.surfsafly.model.Quiz;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.repository.QuizRepository;
import hr.algebra.surfsafly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/quiz")
public class QuizController {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public void saveQuiz(@RequestBody QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        Optional<User> author = userRepository.findUserByUsername(quizDto.getAuthor());
        author.ifPresent(quiz::setAuthor);
        quizRepository.save(quiz);
    }

}
