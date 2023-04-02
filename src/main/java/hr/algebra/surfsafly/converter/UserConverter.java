package hr.algebra.surfsafly.converter;

import hr.algebra.surfsafly.dto.UserDto;
import hr.algebra.surfsafly.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class UserConverter {

    private final ModelMapper modelMapper;

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
