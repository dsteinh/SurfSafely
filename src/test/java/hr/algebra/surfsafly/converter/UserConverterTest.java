package hr.algebra.surfsafly.converter;

import hr.algebra.surfsafly.dto.UserDto;
import hr.algebra.surfsafly.model.Role;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.repository.RoleRepository;
import hr.algebra.surfsafly.repository.UserRepository;
import hr.algebra.surfsafly.util.user.UserDtoMother;
import hr.algebra.surfsafly.util.user.UserMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

class UserConverterTest {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserConverter userConverter;

    @BeforeEach
    void setUp() {
        roleRepository = Mockito.mock(RoleRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        userConverter = new UserConverter(roleRepository, userRepository);
    }

    @Test
    void givenUser_whenConvertToDto_thenReturnUserDto() {
        // arrange
        User user = UserMother.complete().build();

        // act
        UserDto userDto = userConverter.convert(user);

        // assert
        Assertions.assertEquals("jovan", userDto.getUsername());
        Assertions.assertEquals("jovan", userDto.getPassword());
        Assertions.assertEquals("Admin", userDto.getFirstName());
        Assertions.assertEquals("Admin", userDto.getLastName());
        Assertions.assertEquals("admin.admin@mail.hr", userDto.getEmail());
        Assertions.assertEquals(1L, userDto.getRoleId());
    }

    @Test
    void givenUserDtoWithExistingUsername_whenConvertToUser_thenThrowRoleNotFoundException() {
        // arrange
        UserDto userDto = UserDtoMother.complete().build();
        Mockito.when(roleRepository.findById(userDto.getRoleId())).thenReturn(Optional.empty());

        // act and assert
        Assertions.assertThrows(RoleNotFoundException.class, () -> userConverter.convert(userDto));
    }

    @Test
    void givenUserDtoWithNonExistingRole_whenConvertToUser_thenThrowRoleNotFoundException() {
        // arrange
        UserDto userDto = UserDtoMother.complete().build();
        Mockito.when(userRepository.findUserByUsername(userDto.getUsername())).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findById(userDto.getRoleId())).thenReturn(Optional.empty());

        // act and assert
        Assertions.assertThrows(RoleNotFoundException.class, () -> userConverter.convert(userDto));
    }

    @Test
    void givenUserDto_whenConvertToUser_thenReturnUser() throws RoleNotFoundException {
        // arrange
        Role role = Role.builder().id(1L).name("ADMIN").build();
        UserDto userDto = UserDtoMother.complete().build();
        Mockito.when(userRepository.findUserByUsername(userDto.getUsername())).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findById(userDto.getRoleId())).thenReturn(Optional.of(role));

        // act
        User user = userConverter.convert(userDto);

        // assert
        Assertions.assertEquals("jovan", user.getUsername());
        Assertions.assertEquals("jovan", user.getPassword());
        Assertions.assertEquals("Admin", user.getFirstName());
        Assertions.assertEquals("Admin", user.getLastName());
        Assertions.assertEquals("admin.admin@mail.hr", user.getEmail());
        Assertions.assertEquals(role, user.getRole());
    }
}