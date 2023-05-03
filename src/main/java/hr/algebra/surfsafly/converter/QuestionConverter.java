package hr.algebra.surfsafly.converter;

import hr.algebra.surfsafly.dto.QuestionDto;
import hr.algebra.surfsafly.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionConverter {
    AnswerConverter answerConverter;

    public QuestionDto convert(Question question) {
        return QuestionDto.builder()
                .answerDtoList(question.getAnswers()
                        .stream().map(answerConverter::convert).toList())
                .build();
    }

    public Question convert(QuestionDto questionDto) {
        return Question.builder()
                .answers(questionDto.getAnswerDtoList()
                        .stream().map(answerConverter::convert).toList())
                .build();
    }
}
