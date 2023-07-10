package by.ahmed.sweeterapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MessageDto {
    Integer id;
    String text;
    String image;
    UserDto sender;
    UserDto receiver;
    LocalDate date;
}
