package hr.algebra.surfsafly.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuizDto {
    Long id;
    private String title;
    private String description;
    private String author;
    private List<QuestionDto> questionDtoList;
}
