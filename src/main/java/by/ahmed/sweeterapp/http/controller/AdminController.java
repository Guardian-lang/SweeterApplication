package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "users";
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
