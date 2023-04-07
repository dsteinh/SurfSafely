package hr.algebra.surfsafly.util.user;

import hr.algebra.surfsafly.dto.UserDto;

public class UserDtoMother {

    public static UserDto.UserDtoBuilder complete() {
        return UserDto.builder()
                .firstName("Admin")
                .lastName("Admin")
                .username("jovan")
                .password("jovan")
                .roleId(1L)
                .email("admin.admin@mail.hr");

    }

    public static UserDto.UserDtoBuilder usernameAndPasswordAreNull() {
        return UserDto.builder()
                .username(null)
                .password(null);

    }
}
