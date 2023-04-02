package hr.algebra.surfsafly.converter;

import hr.algebra.surfsafly.dto.UserDto;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleNotFoundException;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final RoleRepository roleRepository;

    public UserDto convert(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roleId(user.getRole().getId()).build();
    }

    public User convert(UserDto userDto) throws RoleNotFoundException {
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .role(roleRepository
                        .findById(userDto.getRoleId())
                        .orElseThrow(() -> new RoleNotFoundException("role with given id does not exist")))
                .build();
    }


}
