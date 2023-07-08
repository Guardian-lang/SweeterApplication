package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.entity.User;
import by.ahmed.sweeterapp.mapper.ProfileMapper;
import by.ahmed.sweeterapp.repository.UserRepository;
import by.ahmed.sweeterapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ProfileMapper profileMapper;

    @GetMapping
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("profile", profileMapper.toDto(user));
        return "profile";
    }

    @GetMapping("/edit")
    public String userEditForm(@AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("user", userRepository
                .findByUsername(userDetails.getUsername()).orElseThrow());
        model.addAttribute("roles", Role.values());
        return "edit";
    }

    @PostMapping("/edit")
    public String userEdit(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam String email,
                           @RequestParam String username,
                           @RequestParam String firstname,
                           @RequestParam String lastname,
                           @RequestParam LocalDate birth_date,
                           @RequestParam Gender gender,
                           @RequestParam Role role,
                           @RequestParam MultipartFile avatar,
                           Model model) {
        model.addAttribute("genders", Gender.values());
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setBirth_date(birth_date);
        user.setGender(gender);
        user.setRole(role);
        user.setEmail(email);

        userService.uploadImage(avatar);
        user.setAvatar(avatar.getOriginalFilename());
        userRepository.saveAndFlush(user);

        userRepository.saveAndFlush(user);

        return "redirect:/profile";
    }
}
