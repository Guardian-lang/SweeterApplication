package by.ahmed.sweeterapp.validator;

import by.ahmed.sweeterapp.dto.UserDto;
import by.ahmed.sweeterapp.validator.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<Password, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        Pattern patternForPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}$");
        Matcher matcherForPassword = patternForPassword.matcher(userDto.getPassword());
        return !matcherForPassword.find();
    }
}
