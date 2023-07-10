package by.ahmed.sweeterapp.http.controller.rest;

import by.ahmed.sweeterapp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
public class MessageRestController {
    private final MessageService messageService;

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> findImage(@PathVariable("id") Integer id) {
        return messageService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }
}
