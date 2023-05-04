package hr.algebra.surfsafly.service.impl;

import hr.algebra.surfsafly.model.Quiz;
import hr.algebra.surfsafly.repository.QuizRepository;
import hr.algebra.surfsafly.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    @Override
    public Quiz create(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public void delete(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Quiz> getAll() {
    return quizRepository.getAllBy();
    }
}
