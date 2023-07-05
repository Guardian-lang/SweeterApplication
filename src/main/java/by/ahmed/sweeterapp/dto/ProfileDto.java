package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.utils.CustomMultipartFile;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ProfileDto {
    String username;
    //CustomMultipartFile avatar;
    String firstname;
    String lastname;
    LocalDate birth_date;
    Gender gender;
}
