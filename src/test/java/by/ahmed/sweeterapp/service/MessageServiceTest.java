package by.ahmed.sweeterapp.service;

import by.ahmed.sweeterapp.annotation.IntegrationTesting;
import by.ahmed.sweeterapp.dto.MessageDto;
import by.ahmed.sweeterapp.mapper.MessageMapper;
import by.ahmed.sweeterapp.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTesting
@RequiredArgsConstructor
@Rollback
public class MessageServiceTest {

    private final MessageRepository messageRepository;
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final UserService userService;

    @Test
    void createTest() {
        var message = MessageDto.builder()
                .id(null)
                .text("wowowo")
                .image(null)
                .date(LocalDate.now())
                .sender(userService.findByUsername("Ahmed").orElseThrow())
                .receiver(userService.findByUsername("Sveta").orElseThrow())
                .build();
        var actual = messageService.create(message);
        assertThat(actual.getText()).isEqualTo(message.getText());
        assertThat(messageRepository.findAll()).hasSize(3);
    }

    @Test
    void updateTest() {
        var message = messageRepository.findById(801);
        MessageDto messageDto = messageMapper.toDto(message.get());
        messageDto.setId(null);
        messageDto.setText("wowowow");
        messageDto.setImage(null);
        messageDto.setDate(LocalDate.now());
        messageDto.setSender(userService.findByUsername("Ahmed").orElseThrow());
        messageDto.setReceiver(userService.findByUsername("Sveta").orElseThrow());
        var result = messageService.update(801, messageDto).orElseThrow();
        assertThat(result.getText()).isEqualTo(messageDto.getText());
    }

    @Test
    void deleteTest() {
        messageService.delete(801);
        assertThat(messageRepository.findAll()).hasSize(1);
    }
}
