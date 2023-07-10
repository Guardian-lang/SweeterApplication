package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileDto {
    String username;
    String avatar;
    String firstname;
    String lastname;
    LocalDate birth_date;
    Gender gender;
    Role role;
}
