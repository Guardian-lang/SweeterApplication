package by.ahmed.sweeterapp.mapper;

import by.ahmed.sweeterapp.dto.UserDto;
import by.ahmed.sweeterapp.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserUpdateMapper {

    default User map(UserDto userDto, User user) {
        user.setUsername(userDto.getUsername());
        user.setAvatar(userDto.getAvatar());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setBirth_date(userDto.getBirth_date());
        user.setGender(userDto.getGender());
        user.setRole(userDto.getRole());

        return user;
    }
}
