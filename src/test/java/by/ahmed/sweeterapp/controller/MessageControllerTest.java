package by.ahmed.sweeterapp.controller;

import by.ahmed.sweeterapp.annotation.IntegrationTesting;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTesting
@RequiredArgsConstructor
@Rollback
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN"})
public class MessageControllerTest {
    private final MockMvc mockMvc;

    @Test
    public void mainTest() throws Exception{
        mockMvc.perform(get("/messages")
                .param("filter", "Ahmed"))
                .andExpect(status().isOk())
                .andExpect(view().name("messages"));
    }

    @Test
    public void sendMessageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/messages/{id}/send")
                .param("id", String.valueOf(202L))
                .param("text", "Text")
                .param("date", String.valueOf(LocalDate.now())))
                .andExpect(status().isOk())
                .andExpect(view().name("send"));
    }
}
