package by.ahmed.sweeterapp.validator;

import by.ahmed.sweeterapp.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Optional;

@Component
public class RegistrationValidator implements Validator<Optional<User>> {
    @Override
    public boolean isValid(Optional<User> object) {
        return object.isEmpty();
    }
}
