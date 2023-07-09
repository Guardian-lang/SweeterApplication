package by.ahmed.sweeterapp.repository;

import by.ahmed.sweeterapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByReceiverUsernameAndSenderUsername(String receiver, String username);
    List<Message> findAllByReceiverUsername(String receiver);
    List<Message> findAllBySenderUsername(String sender);
}
