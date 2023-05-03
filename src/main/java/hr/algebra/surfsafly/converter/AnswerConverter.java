package hr.algebra.surfsafly.converter;

import hr.algebra.surfsafly.dto.AnswerDto;
import hr.algebra.surfsafly.dto.QuizDto;
import hr.algebra.surfsafly.model.Answer;
import hr.algebra.surfsafly.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerConverter {
    public AnswerDto convert(Answer answer) {
        return AnswerDto.builder()
                .text(answer.getAnswerText())
                .isCorrect(answer.getIsCorrect()).build();
    }
    public Answer convert(AnswerDto answerDto) {
        return Answer.builder()
                .answerText(answerDto.getText())
                .isCorrect(answerDto.getIsCorrect()).build();
    }
}
