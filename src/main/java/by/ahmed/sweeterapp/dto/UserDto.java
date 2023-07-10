package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Message;
import by.ahmed.sweeterapp.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@NotNull
public class UserDto {
    String username;
    String avatar;
    String firstname;
    String lastname;
    LocalDate birth_date;
    Gender gender;
    String email;
    String password;
    boolean active;
    Role role;
    List<Message> messageList;
}
