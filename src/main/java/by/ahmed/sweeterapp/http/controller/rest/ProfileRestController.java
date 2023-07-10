package by.ahmed.sweeterapp.http.controller.rest;

import by.ahmed.sweeterapp.repository.UserRepository;
import by.ahmed.sweeterapp.service.ImageService;
import by.ahmed.sweeterapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class ProfileRestController {

    private static final String PATH = "C:\\Users\\Ahmed\\IdeaProjects\\sweeter-app\\src\\main\\resources\\images\\default.jpg";
    private final UserRepository userRepository;
    private final UserService userService;
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<byte[]> findAvatar(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findAvatar(userRepository.findByUsername(userDetails.getUsername())
                        .orElseThrow().getId())
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> findOtherAvatar(@PathVariable("id") Long id) {
        return userService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @GetMapping("/default")
    public ResponseEntity<byte[]> getDefaultAvatar() {
        return imageService.get(PATH)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }
}
