package hr.algebra.surfsafly.converter;


import hr.algebra.surfsafly.dto.QuizDto;
import hr.algebra.surfsafly.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizConverter {

    public QuizDto convert(Quiz quiz) {
        return QuizDto.builder()
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .author(quiz.getAuthor().getUsername()).build();
    }


}
