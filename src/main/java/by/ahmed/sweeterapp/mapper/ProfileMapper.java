package by.ahmed.sweeterapp.mapper;

import by.ahmed.sweeterapp.dto.ProfileDto;
import by.ahmed.sweeterapp.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ProfileMapper {
    default ProfileDto toDto(User user) {
        return new ProfileDto(user.getUsername(),
                //user.getAvatar(),
                user.getFirstname(),
                user.getLastname(),
                user.getBirth_date(),
                user.getGender());
    }


}
