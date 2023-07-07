package by.ahmed.sweeterapp.service;

import by.ahmed.sweeterapp.entity.User;
import by.ahmed.sweeterapp.repository.UserRepository;
import by.ahmed.sweeterapp.validator.LoginUserValidator;
import by.ahmed.sweeterapp.validator.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{

    private final UserRepository userRepository;
    private final LoginUserValidator loginUserValidator;
    private final ImageService imageService;

    public Optional<User> login(String username, String password) {
        Optional<User> userDto = userRepository.findAll()
                .stream()
                .filter(it -> it.getUsername()
                        .equals(username)
                        && it.getPassword().equals(password))
                .findFirst();
        var validationResult = loginUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    @SneakyThrows
    public void uploadImage(MultipartFile image) {
        if(!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getAvatar)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
