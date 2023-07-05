package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.validator.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@NotNull
public class UserDto {
    String username;
    @Email
    String email;
    @Password
    String password;
    boolean active;
}
