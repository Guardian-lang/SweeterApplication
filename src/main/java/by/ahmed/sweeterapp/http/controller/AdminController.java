package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.dto.MessageDto;
import by.ahmed.sweeterapp.service.MessageService;
import by.ahmed.sweeterapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final MessageService messageService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "users";
    }

    @GetMapping("/find")
    public String findUserForm() {
        return "find";
    }

    @PostMapping("/find")
    public String findUser(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("username") String username,
                           Model model) {
        var admin = userService.findByUsername(userDetails.getUsername()).get();
        model.addAttribute("adminId", admin.getId());
        if (userService.findByUsername(username).isPresent()) {
            model.addAttribute("user", userService.findByUsername(username).orElseThrow());
        }
        else {
            model.addAttribute("error", "User not found");
            return "users";
        }
        return "find";
    }

    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long id) {
//        var user = userService.findById(id).get();
//        var receivedMessages = messageService.findAllByReceiverUsername(user.getUsername());
//        for (MessageDto message : receivedMessages) {
//            messageService.delete(message.getId());
//        }
//        var sentMessages = messageService.findAllBySenderUsername(user.getUsername());
//        for (MessageDto message : sentMessages) {
//            messageService.delete(message.getId());
//        }
        userService.delete(id);
        return "redirect:/users";
    }
}
