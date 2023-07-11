package by.ahmed.sweeterapp.repository;

import by.ahmed.sweeterapp.annotation.IntegrationTesting;
import by.ahmed.sweeterapp.entity.Message;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTesting
@RequiredArgsConstructor
@Rollback
public class MessageRepositoryTest {

    private final MessageRepository messageRepository;

    @Test
    public void findAllTest() {
        List<Message> messages = messageRepository.findAll();
        assertThat(2).isEqualTo(messages.size());
    }

    @Test
    void findBySenderUsernameAndReceiverUsernameTest() {
        List<Message> messages = messageRepository
                .findAllByReceiverUsernameAndSenderUsername(
                        "Sveta", "Ahmed");
        assertThat(1).isEqualTo(messages.size());
    }

    @Test
    void findBySenderUsernameTest() {
        List<Message> messages = messageRepository
                .findAllBySenderUsername("Ahmed");
        assertThat(1).isEqualTo(messages.size());
    }

    @Test
    void findByReceiverUsernameTest() {
        List<Message> messages = messageRepository
                .findAllByReceiverUsername("Ahmed");
        assertThat(1).isEqualTo(messages.size());
    }
}
