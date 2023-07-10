package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "users";
    }

    @PostMapping("/find")
    public String findUser(@RequestParam("username") String username,
                           Model model) {
        if (userService.findByUsername(username).isPresent()) {
            model.addAttribute("user", userService.findByUsername(username).orElseThrow());
        }
        else {
            model.addAttribute("error", "Cannot find user");
            return "users";
        }
        return "find";
    }

    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
