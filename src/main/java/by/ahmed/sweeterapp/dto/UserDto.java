package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Message;
import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.validator.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Value
@NotNull
public class UserDto {
    String username;
    String firstname;
    String lastname;
    LocalDate birth_date;
    Gender gender;
    @Email
    String email;
    @Password
    String password;
    boolean active;
    Set<Role> roles;
    List<Message> messageList;
}
