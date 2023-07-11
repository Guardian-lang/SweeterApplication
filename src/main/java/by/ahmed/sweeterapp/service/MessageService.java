package by.ahmed.sweeterapp.service;

import by.ahmed.sweeterapp.dto.MessageDto;
import by.ahmed.sweeterapp.entity.Message;
import by.ahmed.sweeterapp.mapper.MessageMapper;
import by.ahmed.sweeterapp.mapper.MessageUpdateMapper;
import by.ahmed.sweeterapp.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ImageService imageService;
    private final MessageMapper messageMapper;
    private final MessageUpdateMapper messageUpdateMapper;

    public Optional<MessageDto> findById(Integer id) {
        return messageRepository.findById(id)
                .map(messageMapper::toDto);
    }

    public List<MessageDto> findAllBySenderUsernameAndReceiverUsername(String sender, String receiver) {
        return messageRepository.findAllByReceiverUsernameAndSenderUsername(receiver, sender).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    public List<MessageDto> findAllBySenderUsername(String sender) {
        return messageRepository.findAllBySenderUsername(sender).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    public List<MessageDto> findAllByReceiverUsername(String receiver) {
        return messageRepository.findAllByReceiverUsername(receiver).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    @Transactional
    public MessageDto create(MessageDto messageDto) {
        return Optional.of(messageDto)
                .map(messageMapper::toMessage)
                .map(messageRepository::save)
                .map(messageMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<MessageDto> update(Integer id, MessageDto messageDto) {
        return messageRepository.findById(id)
                .map(entity -> {
                    entity.setImage(messageDto.getImage());
                    return messageUpdateMapper.map(messageDto, entity);
                })
                .map(messageRepository::saveAndFlush)
                .map(messageMapper::toDto);
    }

    @Transactional
    public boolean delete(Integer id) {
        return messageRepository.findById(id)
                .map(entity -> {
                    messageRepository.delete(entity);
                    messageRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @SneakyThrows
    public void uploadImage(MultipartFile image) {
        if(!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findImage(Integer id) {
        return messageRepository.findById(id)
                .map(Message::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
