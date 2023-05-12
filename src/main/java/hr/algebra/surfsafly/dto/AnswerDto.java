package hr.algebra.surfsafly.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDto {
    Long id;
    String text;
    Boolean isCorrect;
    Long questionId;
}
