package by.ahmed.sweeterapp.repository;

import by.ahmed.sweeterapp.annotation.IntegrationTesting;
import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@IntegrationTesting
@RequiredArgsConstructor
@Rollback
public class UserRepositoryTest {
    private final EntityManager entityManager;

    private final UserRepository userRepository;


    @Test
    public void testSave() {
        User user = User.builder()
                        .username("AndreiBor")
                        .firstname("Андрей")
                        .lastname("Борисов")
                        .birth_date(LocalDate.of(1987, 8, 6))
                        .gender(Gender.MALE)
                        .role(Role.USER)
                        .build();
        userRepository.save(user);
        entityManager.flush();
        assertThat(userRepository.findAll().size()).isEqualTo(5);
    }

    @Test
    public void testFindAll() {
        List<User> results = userRepository.findAll();
        List<String> fullNames = results.stream().map(User::getUsername).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Sveta",
                "IvanovIvan",
                "Ahmed",
                "MegaIgor");
    }

    @Test
    public void testUpdate() {
        User user = userRepository.findById(202L).isPresent()
                ? userRepository.findById(202L).get()
                : fail("user is null");
        user.setEmail("Ghsdsdst4@mail.ru");
        userRepository.save(user);
        User user1 = userRepository.findById(202L).isPresent()
                ? userRepository.findById(202L).get()
                : fail("user1 is null");
        assertThat(user1.getEmail()).isEqualTo("Ghsdsdst4@mail.ru");
    }


    @Test
    public void testFindById() {
        User user = userRepository.findById(202L).isPresent()
                ? userRepository.findById(202L).get() : null;
        assert user != null;
        assertThat(user.getUsername()).isEqualTo("Sveta");
    }

    @Test
    public void testFindByUsername() {
        User user = userRepository.findByUsername("Sveta").isPresent()
                ? userRepository.findByUsername("Sveta").get() : null;
        assert user != null;
        assertThat(user.getId()).isEqualTo(202L);
    }
}
