package by.ahmed.sweeterapp.mapper;

import by.ahmed.sweeterapp.dto.MessageDto;
import by.ahmed.sweeterapp.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageUpdateMapper {

    private final UserMapper userMapper;

    public Message map(MessageDto messageDto, Message message) {
        message.setText(messageDto.getText());
        message.setImage(messageDto.getImage());
        message.setDate(messageDto.getDate());
        message.setSender(userMapper.toUser(messageDto.getSender()));
        message.setReceiver(userMapper.toUser(messageDto.getReceiver()));

        return message;
    }
}
