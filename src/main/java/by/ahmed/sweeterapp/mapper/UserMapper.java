package by.ahmed.sweeterapp.mapper;

import by.ahmed.sweeterapp.dto.UserDto;
import by.ahmed.sweeterapp.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto dto);
    UserDto toDto(User user);
}
