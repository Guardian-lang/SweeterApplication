package by.ahmed.sweeterapp.http.controller;

import by.ahmed.sweeterapp.entity.Message;
import by.ahmed.sweeterapp.repository.MessageRepository;
import by.ahmed.sweeterapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String main(@RequestParam(required = false,
            name = "filter", defaultValue = "") String filter, Model model) {
        List<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        return "messages";
    }

    @PostMapping("/add")
    public String addMessage(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam String text,
                             @RequestParam String tag,
                              HttpServletResponse response) throws IOException {
        var message = new Message(text, tag, LocalDate.now(), userRepository
                .findByUsername(userDetails.getUsername()).orElseThrow());
        messageRepository.save(message);
        response.sendRedirect("/messages");
        return "messages";
    }

    @PostMapping("/{id}/delete")
    public String deleteMessage(@PathVariable("id") Integer id) {
        messageRepository.deleteById(id);
        return "redirect:/messages";
    }
}
