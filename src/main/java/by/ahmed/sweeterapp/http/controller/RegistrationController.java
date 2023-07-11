package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.dto.RegistrationDto;
import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.entity.User;
import by.ahmed.sweeterapp.mapper.UserMapper;
import by.ahmed.sweeterapp.repository.UserRepository;
import by.ahmed.sweeterapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

import static by.ahmed.sweeterapp.util.ModelHelper.redirectAttributes;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(RegistrationDto user, Model model, RedirectAttributes redirectAttributes) {
        var userFromDb = userService.findByUsername(user.getUsername());
        model.addAttribute("genders", Gender.values());
        if (userFromDb.isPresent()) {
            model.addAttribute("error", "This user already exists!");
            redirectAttributes(redirectAttributes, user);
            return "redirect:/registration";
        }

        user.setActive(true);
        user.setRole(Role.USER);
        userService.create(user);

        return "redirect:/login";
    }
}
