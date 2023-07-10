package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.validator.annotation.Password;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
public class RegistrationDto {
    String username;
    String firstname;
    String lastname;
    String birth_date;
    Gender gender;
    Role role;
    boolean active;
    @Email
    String email;
    @Password
    String password;
}
