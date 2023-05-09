package hr.algebra.surfsafly.service;

import hr.algebra.surfsafly.model.Quiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface QuizService {
    Quiz create(Quiz quiz);

    @Transactional
    void delete(Long id);

    Quiz getQuizById(Long id);

    List<Quiz> getAll();

    void deleteAll();
}
