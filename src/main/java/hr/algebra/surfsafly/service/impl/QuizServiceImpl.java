package hr.algebra.surfsafly.service.impl;

import hr.algebra.surfsafly.dto.SolveAttemptDto;
import hr.algebra.surfsafly.model.Answer;
import hr.algebra.surfsafly.model.Question;
import hr.algebra.surfsafly.model.Quiz;
import hr.algebra.surfsafly.repository.AnswerRepository;
import hr.algebra.surfsafly.repository.QuestionRepository;
import hr.algebra.surfsafly.repository.QuizRepository;
import hr.algebra.surfsafly.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    final QuizRepository quizRepository;
    final QuestionRepository questionRepository;
    final AnswerRepository answerRepository;

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

    @Override
    public void deleteAll() {
        quizRepository.deleteAll();
    }

    @Override
    public Double calculateResults(SolveAttemptDto solveAttemptDto) {
        List<Answer> answers = answerRepository.findAllById(solveAttemptDto.getAnswerIds());
        List<Question> questions = answers.get(0).getQuestion().getQuiz().getQuestions();

        long numberOfAnswers = questions.stream()
                .mapToLong(q -> q.getAnswers()
                        .stream()
                        .filter(Answer::getIsCorrect)
                        .count()).sum();
        long i = answers.stream().filter(Answer::getIsCorrect).count();
        return ((double) i) / numberOfAnswers;
    }
}
