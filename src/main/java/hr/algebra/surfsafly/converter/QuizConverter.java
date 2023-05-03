package hr.algebra.surfsafly.converter;


import hr.algebra.surfsafly.dto.QuizDto;
import hr.algebra.surfsafly.model.Quiz;
import hr.algebra.surfsafly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizConverter {

    private final QuestionConverter questionConverter;
    private final UserRepository userRepository;

    public QuizDto convert(Quiz quiz) {
        return QuizDto.builder()
                .questionDtoList(quiz.getQuestions()
                        .stream().map(questionConverter::convert).toList())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .author(quiz.getAuthor().getUsername())
                .build();
    }
    public Quiz convert(QuizDto quizDto) {
        return Quiz.builder()
                .description(quizDto.getDescription())
                .questions(quizDto.getQuestionDtoList()
                        .stream().map(questionConverter::convert).toList())
                .title(quizDto.getTitle())
                .description(quizDto.getDescription())
                .author(userRepository.findUserByUsername(quizDto.getAuthor()).orElseThrow())
                .build();
    }


}
