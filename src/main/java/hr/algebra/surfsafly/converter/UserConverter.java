package hr.algebra.surfsafly.converter;

import hr.algebra.surfsafly.dto.UserDto;
import hr.algebra.surfsafly.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final ModelMapper modelMapper;

    public UserDto convert(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User convert(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
