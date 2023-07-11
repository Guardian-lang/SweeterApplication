package by.ahmed.sweeterapp.controller;

import by.ahmed.sweeterapp.annotation.IntegrationTesting;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTesting
@RequiredArgsConstructor
@Rollback
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN"})
public class AdminControllerTest {
    private final MockMvc mockMvc;

    @Test
    public void userListTest() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("/users"));
    }

    @Test
    public void findUserTest() throws Exception {
        mockMvc.perform(post("/users/find")
                .param("username", "Ahmed"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/find"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(post("/users/{userId}/delete")
                        .param("userId", String.valueOf(202L)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }
}
