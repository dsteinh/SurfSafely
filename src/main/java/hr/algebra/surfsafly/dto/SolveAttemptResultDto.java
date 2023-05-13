package hr.algebra.surfsafly.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolveAttemptResultDto {
    Double correctnessPercentage;
    Long quizId;
}
