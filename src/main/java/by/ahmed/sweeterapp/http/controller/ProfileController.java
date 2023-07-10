package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.mapper.ProfileMapper;
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

    private final UserService userService;
    private final ProfileMapper profileMapper;

    @GetMapping
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        var userDto = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("profile", profileMapper.toProfileDto(userDto));
        return "profile";
    }

    @GetMapping("/edit")
    public String userEditForm(@AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("user", userService
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
        var userDto = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", userDto);
        userDto.setUsername(username);
        userDto.setFirstname(firstname);
        userDto.setLastname(lastname);
        userDto.setBirth_date(birth_date);
        userDto.setGender(gender);
        userDto.setRole(role);
        userDto.setEmail(email);

        userService.uploadImage(avatar);
        userDto.setAvatar(avatar.getOriginalFilename());
        userService.update(userDto.getId(), userDto);

        return "redirect:/profile";
    }
}
