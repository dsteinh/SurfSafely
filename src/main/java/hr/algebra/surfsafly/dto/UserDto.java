package hr.algebra.surfsafly.dto;

import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
}
