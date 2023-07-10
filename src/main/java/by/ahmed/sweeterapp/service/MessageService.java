package by.ahmed.sweeterapp.service;

import by.ahmed.sweeterapp.entity.Message;
import by.ahmed.sweeterapp.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ImageService imageService;

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
