package hr.algebra.surfsafly.util.user;

import hr.algebra.surfsafly.model.Role;
import hr.algebra.surfsafly.model.User;

public class UserMother {

    public static User.UserBuilder complete() {
        return User.builder()
                .id(1L)
                .firstName("Admin")
                .lastName("Admin")
                .username("jovan")
                .password("jovan")
                .role(Role.builder().id(1L).name("ADMIN").build())
                .email("admin.admin@mail.hr");

    }
}
