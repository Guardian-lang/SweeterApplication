package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.entity.Message;
import by.ahmed.sweeterapp.entity.User;
import by.ahmed.sweeterapp.repository.MessageRepository;
import by.ahmed.sweeterapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"user"})
public class MainController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false,
            name = "filter", defaultValue = "") String filter,
            Model model) {
        List<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        return "main";
    }

    @PostMapping("/main/add")
    public String addMessage(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String text,
            @RequestParam String tag,
            Model model) {
        var message = new Message(text, tag, LocalDate.now(), userRepository
                .findByUsername(userDetails.getUsername()).orElseThrow());
        messageRepository.save(message);
        return "main";
    }

    @PostMapping("/main/{id}/delete")
    public String deleteMessage(@PathVariable("id") Integer id) {
        messageRepository.deleteById(id);
        return "redirect:/main";
    }
}
