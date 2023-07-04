package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.entity.User;
import lombok.Value;

import java.time.LocalDate;

@Value
public class MessageDto {
    String text;
    User author;
    LocalDate date;
}
