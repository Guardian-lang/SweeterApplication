package by.ahmed.sweeterapp.validator;

import by.ahmed.sweeterapp.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginUserValidator {
    public ValidationResult isValid(Optional<UserDto> object) {
        var validationResult = new ValidationResult();
        if (object.isEmpty()) {
            validationResult.add(Error.of(Error.getMessage("username"), "Password or Login is wrong. "));
        }
        return validationResult;
    }
}
