package by.ahmed.sweeterapp.mapper;

import by.ahmed.sweeterapp.dto.MessageDto;
import by.ahmed.sweeterapp.entity.Message;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toMessage(MessageDto dto);
    MessageDto toDto(Message message);
}
