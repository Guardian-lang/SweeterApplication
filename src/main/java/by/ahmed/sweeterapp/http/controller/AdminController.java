package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "users";
    }

    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
