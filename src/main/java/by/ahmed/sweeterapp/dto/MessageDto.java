package by.ahmed.sweeterapp.dto;

import by.ahmed.sweeterapp.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@NotNull
public class MessageDto {
    String text;
    User author;
    LocalDate date;
}
