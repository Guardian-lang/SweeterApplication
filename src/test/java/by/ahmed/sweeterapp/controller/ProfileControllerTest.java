package by.ahmed.sweeterapp.controller;

import by.ahmed.sweeterapp.annotation.IntegrationTesting;
import by.ahmed.sweeterapp.entity.Gender;
import by.ahmed.sweeterapp.entity.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTesting
@RequiredArgsConstructor
@Rollback
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN"})
public class ProfileControllerTest {
    private final MockMvc mockMvc;

    @Test
    public void userEditTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/edit")
                        .param("id", String.valueOf(202L))
                        .param("email", "sdfghj@gmail.com")
                        .param("username", "Username")
                        .param("firstname", "Firstname")
                        .param("lastname", "Lastname")
                        .param("birth_date", String.valueOf(LocalDate.now()))
                        .param("gender", Gender.FEMALE.name())
                        .param("role", Role.USER.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));
    }
}
