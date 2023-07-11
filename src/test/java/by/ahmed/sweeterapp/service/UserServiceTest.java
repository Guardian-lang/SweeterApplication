package by.ahmed.sweeterapp.service;

import by.ahmed.sweeterapp.annotation.IntegrationTesting;
import by.ahmed.sweeterapp.dto.RegistrationDto;
import by.ahmed.sweeterapp.dto.UserDto;
import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTesting
@RequiredArgsConstructor
@Rollback
public class UserServiceTest {

    private final UserRepository userRepository;
    private final UserService userService;

    @Test
    public void createTest() {
        var user = new RegistrationDto("username",
                "Firstname",
                "Lastname",
                LocalDate.now(),
                Gender.MALE,
                Role.USER,
                true,
                "uytrewerty@gmail.com",
                "$%&^%123"
        );
        var actual = userService.create(user);
        assertThat(user.getUsername()).isEqualTo(actual.getUsername());
        assertThat(userRepository.findAll()).hasSize(5);
    }

    @Test
    public void updateTest() {
        var user = UserDto.builder()
                .username("Username")
                .firstname("Firstname")
                .lastname("Lastname")
                .birth_date(LocalDate.now())
                .gender(Gender.MALE)
                .role(Role.USER)
                .active(true)
                .email("uytrewerty@gmail.com")
                .password("$%&^%123")
                .build();
        var actual = userService.update(202L, user).orElseThrow();
        assertThat(actual.getUsername()).isNotEqualTo(user.getUsername());
    }

    @Test
    public void deleteTest() {
        var actual = userService.delete(202L);
        assertThat(actual).isTrue();
        assertThat(userRepository.findAll()).hasSize(3);
    }
}
