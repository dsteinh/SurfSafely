package hr.algebra.surfsafly.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionDto {
    String questionText;
    List<AnswerDto> answerDtoList;
    Long quizId;
}
