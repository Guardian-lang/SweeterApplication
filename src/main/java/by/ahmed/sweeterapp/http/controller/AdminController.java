package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.entity.User;
import by.ahmed.sweeterapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "users";
    }

    @GetMapping("/users/{userId}")
    public String userEditForm(@PathVariable("userId") Long id,
            Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow());
        model.addAttribute("roles", Role.values());
        return "edit";
    }

    @PostMapping("/users/{userId}/save")
    public String userSave(@PathVariable("userId") Long id,
                           @RequestParam Map<String, String> form,
                           @RequestParam String email,
                           @RequestParam String username) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(username);
        user.setEmail(email);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if(roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.saveAndFlush(user);

        return "redirect:/users";
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
