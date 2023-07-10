package by.ahmed.sweeterapp.util;

import by.ahmed.sweeterapp.dto.RegistrationDto;
import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@UtilityClass
public class ModelHelper {

    public static void redirectAttributes(RedirectAttributes redirectAttributes, RegistrationDto user) {
        if (user.getUsername() != null) redirectAttributes.addFlashAttribute("username",
                user.getUsername());
        if (user.getFirstname() != null) redirectAttributes.addFlashAttribute("firstname",
                user.getFirstname());
        if (user.getLastname() != null) redirectAttributes.addFlashAttribute("lastname",
                user.getLastname());
        if (user.getBirth_date() != null) redirectAttributes.addFlashAttribute("birth_date",
                user.getBirth_date());
        if (user.getGender() != null) redirectAttributes.addFlashAttribute("gender",
                user.getGender());
    }
}
