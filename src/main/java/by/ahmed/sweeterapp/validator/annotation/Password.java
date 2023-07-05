package by.ahmed.sweeterapp.validator.annotation;

import by.ahmed.sweeterapp.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Password is simple. " +
            "The password must be at least 8 characters long, contain at least 1 uppercase letter, " +
            "1 lowercase letter and EITHER a special character OR a number. ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
