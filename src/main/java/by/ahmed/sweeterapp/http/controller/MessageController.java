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
@RequestMapping("/messages")
@RequiredArgsConstructor
@SessionAttributes({"receiver"})
public class MessageController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String main(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false,
            name = "filter", defaultValue = "") String filter, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        model.addAttribute("userId", user.getId());
        model.addAttribute("users", userRepository.findAll());
        List<Message> gotmessages;
        List<Message> sentmessages = messageRepository.findAllBySenderUsername(userDetails.getUsername());
        if (filter != null && !filter.isEmpty()) {
            gotmessages = messageRepository.findAllByReceiverUsernameAndSenderUsername(userDetails.getUsername(), filter);
        } else {
            gotmessages = messageRepository.findAllByReceiverUsername(userDetails.getUsername());
        }
        model.addAttribute("gotmessages", gotmessages);
        model.addAttribute("sentmessages", sentmessages);
        return "messages";
    }

    @GetMapping("/{id}/send")
    public String sendMessageForm(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable("id") Long id,
                                  Model model) {
        User receiver = userRepository.findById(id).orElseThrow();
        model.addAttribute("receiver", receiver);
        model.addAttribute("messages", messageRepository
                .findAllByReceiverUsernameAndSenderUsername(receiver.getUsername(), userDetails.getUsername()));
        return "send";
    }

    @PostMapping("/{id}/send")
    public String sendMessage(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable("id") Long id,
                              @RequestParam("text") String text,
                              Model model) {
        var answer = new Message(text, LocalDate.now(), userRepository
                .findByUsername(userDetails.getUsername()).get(), userRepository
                .findById(id).orElseThrow());
        messageRepository.save(answer);
        model.addAttribute("answer", answer);
        return "send";
    }

    @GetMapping("/{id}/update")
    public String editMessageForm(@PathVariable Integer id,
            Model model) {
        model.addAttribute("oldMessage", messageRepository.findById(id).get());
        return "update";
    }

    @PostMapping("/{id}/update")
    public String editMessage(@PathVariable("id") Integer id,
                              @RequestParam String text) {
        var message = messageRepository.findById(id).get();
        message.setText(text);
        messageRepository.saveAndFlush(message);
        return "redirect:/messages";
    }

    @PostMapping("/{id}/delete")
    public String deleteMessage(@PathVariable("id") Integer id) {
        messageRepository.deleteById(id);
        return "redirect:/messages";
    }
}
