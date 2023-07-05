package by.ahmed.sweeterapp.validator;

import by.ahmed.sweeterapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegistrationValidator {
    public boolean isValid(Optional<User> object) {
        return object.isEmpty();
    }
}
