package hr.algebra.surfsafly.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizDto {
    private String title;
    private String description;
    private String author;
}
