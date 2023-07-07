package by.ahmed.sweeterapp.mapper;

import by.ahmed.sweeterapp.dto.ProfileDto;
import by.ahmed.sweeterapp.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto toDto(User user);
}
