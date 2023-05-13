package hr.algebra.surfsafly.converter;

import hr.algebra.surfsafly.dto.QuestionDto;
import hr.algebra.surfsafly.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class QuestionConverter {
    final AnswerConverter answerConverter;

    public QuestionDto convert(Question question) {
        return QuestionDto.builder()
                .quizId(Objects.nonNull(question.getId()) ? question.getId() : null)
                .questionText(question.getQuestionText())
                .answerDtoList(question.getAnswers()
                        .stream().map(answerConverter::convert).toList())
                .quizId(question.getQuiz().getId())
                .build();
    }

    public Question convert(QuestionDto questionDto) {
        return Question.builder()
                .id(Objects.nonNull(questionDto.getId()) ? questionDto.getId() : null)
                .questionText(questionDto.getQuestionText())
                .answers(questionDto.getAnswerDtoList()
                        .stream().map(answerConverter::convert).toList())
                .build();
    }
}
