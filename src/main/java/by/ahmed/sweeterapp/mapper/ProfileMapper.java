package by.ahmed.sweeterapp.mapper;

import by.ahmed.sweeterapp.dto.ProfileDto;
import by.ahmed.sweeterapp.dto.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto toProfileDto(UserDto userDto);

}
