package hr.algebra.surfsafly.service.impl;

import hr.algebra.surfsafly.model.Quiz;
import hr.algebra.surfsafly.repository.QuizRepository;
import hr.algebra.surfsafly.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    @Override
    public void saveQuiz(Quiz quiz) {
    quizRepository.save(quiz);
    }
}
