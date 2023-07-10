package by.ahmed.sweeterapp.mapper;

import by.ahmed.sweeterapp.dto.RegistrationDto;
import by.ahmed.sweeterapp.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    RegistrationDto toDto(User user);
    User toUser(RegistrationDto registrationDto);
}
