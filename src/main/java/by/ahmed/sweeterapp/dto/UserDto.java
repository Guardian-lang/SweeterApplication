package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Message;
import by.ahmed.sweeterapp.entity.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    Long id;
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
