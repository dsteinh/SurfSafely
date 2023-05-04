package hr.algebra.surfsafly.service;

import hr.algebra.surfsafly.model.Quiz;
import org.springframework.stereotype.Service;

@Service
public interface QuizService {
    Quiz create(Quiz quiz);
}
