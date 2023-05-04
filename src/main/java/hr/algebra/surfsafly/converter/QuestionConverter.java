package hr.algebra.surfsafly.converter;

import hr.algebra.surfsafly.dto.QuestionDto;
import hr.algebra.surfsafly.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionConverter {
    final AnswerConverter answerConverter;

    public QuestionDto convert(Question question) {
        return QuestionDto.builder()
                .questionText(question.getQuestionText())
                .answerDtoList(question.getAnswers()
                        .stream().map(answerConverter::convert).toList())
                .build();
    }

    public Question convert(QuestionDto questionDto) {
        return Question.builder()
                .questionText(questionDto.getQuestionText())
                .answers(questionDto.getAnswerDtoList()
                        .stream().map(answerConverter::convert).toList())
                .build();
    }
}
