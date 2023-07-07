package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.entity.Gender;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ProfileDto {
    String username;
    String avatar;
    String firstname;
    String lastname;
    LocalDate birth_date;
    Gender gender;
}