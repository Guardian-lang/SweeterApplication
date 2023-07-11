package by.ahmed.sweeterapp.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    Integer id;
    String text;
    String image;
    UserDto sender;
    UserDto receiver;
    LocalDate date;
}
