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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
@SessionAttributes({"receiver"})
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping
    public String main(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false,
            name = "filter", defaultValue = "") String filter, Model model) {
        var user = userService.findByUsername(userDetails.getUsername()).get();
        model.addAttribute("userId", user.getId());
        model.addAttribute("users", userService.findAll());
        List<MessageDto> gotmessages;
        List<MessageDto> sentmessages = messageService.findAllBySenderUsername(userDetails.getUsername());
        if (filter != null && !filter.isEmpty()) {
            gotmessages = messageService.findAllBySenderUsernameAndReceiverUsername(userDetails.getUsername(), filter);
        } else {
            gotmessages = messageService.findAllByReceiverUsername(userDetails.getUsername());
        }
        model.addAttribute("gotmessages", gotmessages);
        model.addAttribute("sentmessages", sentmessages);
        return "messages";
    }

    @GetMapping("/{id}/send")
    public String sendMessageForm(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable("id") Long id,
                                  Model model) {
        var receiver = userService.findById(id).orElseThrow();
        model.addAttribute("receiver", receiver);
        model.addAttribute("messages", messageService
                .findAllBySenderUsernameAndReceiverUsername(receiver.getUsername(), userDetails.getUsername()));
        return "send";
    }

    @PostMapping("/{id}/send")
    public String sendMessage(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable("id") Long id,
                              @RequestParam("text") String text,
                              @RequestParam("image") MultipartFile image,
                              Model model) {
        var answer = new MessageDto();
        messageService.uploadImage(image);
        answer.setText(text);
        answer.setDate(LocalDate.now());
        answer.setImage(image.getOriginalFilename());
        answer.setSender(userService.findByUsername(userDetails.getUsername()).get());
        answer.setReceiver(userService.findById(id).orElseThrow());
        messageService.create(answer);
        model.addAttribute("answer", answer);
        return "send";
    }

    @GetMapping("/{id}/update")
    public String editMessageForm(@PathVariable Integer id,
            Model model) {
        model.addAttribute("oldMessage", messageService.findById(id).get());
        return "update";
    }

    @PostMapping("/{id}/update")
    public String editMessage(@PathVariable("id") Integer id,
                              @RequestParam("text") String text,
                              @RequestParam("image") MultipartFile image) {
        var message = messageService.findById(id).get();
        message.setId(id);
        message.setText(text);
        messageService.uploadImage(image);
        message.setImage(image.getOriginalFilename());
        messageService.update(id, message);
        return "redirect:/messages";
    }

    @PostMapping("/{id}/delete")
    public String deleteMessage(@PathVariable("id") Integer id) {
        messageService.delete(id);
        return "redirect:/messages";
    }
}
