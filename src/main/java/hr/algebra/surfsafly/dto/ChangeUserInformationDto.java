package hr.algebra.surfsafly.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeUserInformationDto {
    private String newFistName;
    private String newLastName;
    private String newEmail;
}
