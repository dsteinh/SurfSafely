package hr.algebra.surfsafly.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaderboardDto {
    private String username;
    private Long score;
    private Long money;

}
