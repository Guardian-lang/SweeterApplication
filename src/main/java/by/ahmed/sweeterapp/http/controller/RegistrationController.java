package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.dto.RegistrationDto;
import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static by.ahmed.sweeterapp.util.ModelHelper.redirectAttributes;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
@SessionAttributes({"error"})
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("error", " ");
        return "registration";
    }

    @PostMapping
    public String addUser(RegistrationDto user, Model model, RedirectAttributes redirectAttributes) {
        var userFromDb = userService.findByUsername(user.getUsername());
        if (userFromDb.isPresent()) {
            model.addAttribute("error", "This user already exists!");
            redirectAttributes(redirectAttributes, user);
            return "redirect:/registration";
        }
        if (user.getUsername().isEmpty()
        || user.getFirstname().isEmpty()
        || user.getLastname().isEmpty()
        || user.getBirth_date().toString().isEmpty()
        || user.getGender().toString().isEmpty()
        || user.getEmail().isEmpty()
        || user.getPassword().isEmpty()) {
            model.addAttribute("error", "Some labels are empty. Please, enter a full data!");
            redirectAttributes(redirectAttributes, user);
            return "redirect:/registration";
        }

        user.setActive(true);
        user.setRole(Role.USER);
        userService.create(user);

        return "redirect:/login";
    }
}
