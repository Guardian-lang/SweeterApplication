package by.ahmed.sweeterapp.service;

import by.ahmed.sweeterapp.dto.RegistrationDto;
import by.ahmed.sweeterapp.dto.UserDto;
import by.ahmed.sweeterapp.entity.User;
import by.ahmed.sweeterapp.mapper.RegistrationMapper;
import by.ahmed.sweeterapp.mapper.UserMapper;
import by.ahmed.sweeterapp.mapper.UserUpdateMapper;
import by.ahmed.sweeterapp.repository.UserRepository;
import by.ahmed.sweeterapp.validator.LoginUserValidator;
import by.ahmed.sweeterapp.validator.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{

    private final UserRepository userRepository;
    private final LoginUserValidator loginUserValidator;
    private final ImageService imageService;
    private final UserMapper userMapper;
    private final RegistrationMapper registrationMapper;
    private final UserUpdateMapper userUpdateMapper;

    public Optional<UserDto> login(String username, String password) {
        var user = userRepository.findAll()
                .stream()
                .filter(it -> it.getUsername()
                        .equals(username)
                        && it.getPassword().equals(password))
                .findFirst()
                .orElseThrow();
        var userDto = Optional.of(userMapper.toDto(user));
        var validationResult = loginUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        return userDto;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto);
    }

    @Transactional
    public UserDto create(RegistrationDto registrationDto) {
        return Optional.of(registrationDto)
                .map(registrationMapper::toUser)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserDto> update(Long id, UserDto userDto) {
        return userRepository.findById(id)
                .map(entity -> {
                    entity.setAvatar(userDto.getAvatar());
                    return userUpdateMapper.map(userDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::toDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
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
