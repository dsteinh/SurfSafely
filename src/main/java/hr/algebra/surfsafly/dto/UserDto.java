package hr.algebra.surfsafly.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Long roleId;
}
