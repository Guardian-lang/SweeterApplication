package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.entity.Role;
import by.ahmed.sweeterapp.entity.User;
import by.ahmed.sweeterapp.service.UserService;
import by.ahmed.sweeterapp.validator.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"user", "errors"})
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }

    @PostMapping("/login")
    public String validate(Model model, String username,
                           String password) {
        try {
            var user = userService.login(username, password);
            return user.map(userData -> onLoginSuccess(model, userData)).orElse(loginFail());
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getErrors());
            return "redirect:/login";
        }
    }

    @GetMapping("login/fail")
    public String loginFail() {
        return "redirect:/login";
    }


    @SneakyThrows
    private String onLoginSuccess(Model model,
                                  User user) {
        model.addAttribute("user", user);
        List<Role> roles = user.getRoles().stream().toList();
        for (Role role : roles) {
            if (role.equals(Role.ADMIN)) {
                return "redirect:/users";
            }
        }
        return "redirect:/main";
    }
}
