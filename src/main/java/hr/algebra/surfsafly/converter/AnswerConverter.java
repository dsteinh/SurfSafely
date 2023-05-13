package hr.algebra.surfsafly.converter;

import hr.algebra.surfsafly.dto.AnswerDto;
import hr.algebra.surfsafly.model.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AnswerConverter {
    public AnswerDto convert(Answer answer) {
        return AnswerDto.builder()
                .id(Objects.nonNull(answer.getId()) ? answer.getId() : null)
                .text(answer.getAnswerText())
                .questionId(answer.getQuestion().getId())
                .isCorrect(answer.getIsCorrect()).build();
    }

    public Answer convert(AnswerDto answerDto) {
        return Answer.builder()
                .id(Objects.nonNull(answerDto.getId()) ? answerDto.getId() : null)
                .answerText(answerDto.getText())
                .isCorrect(answerDto.getIsCorrect()).build();
    }
}
