package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.entity.User;
import by.ahmed.sweeterapp.repository.UserRepository;
import by.ahmed.sweeterapp.service.UserService;
import by.ahmed.sweeterapp.utils.CustomMultipartFile;
import by.ahmed.sweeterapp.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final RegistrationValidator registrationValidator;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(@RequestParam User user, @RequestParam CustomMultipartFile avatar, Model model) {
        model.addAttribute("genders", Gender.values());
        if (!registrationValidator.isValid(userRepository.findByUsername(user.getUsername()))) {
            model.addAttribute("user", userRepository.findByUsername(user.getUsername()).get());
            model.addAttribute("error", "This user already exists.");
            return "registration";
        }
        else {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setMessages(new ArrayList<>());
            userService.uploadImage(avatar);
            user.setAvatar("C://Users//Ahmed//IdeaProjects//sweeter-app//src//main//resources//images//"
                    + avatar.getOriginalFilename());
            userRepository.save(user);
            return "redirect:/login";
        }
    }
}
