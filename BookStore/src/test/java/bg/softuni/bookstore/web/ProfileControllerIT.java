package bg.softuni.bookstore.web;

import bg.softuni.bookstore.application.services.ProfileService;
import bg.softuni.bookstore.model.dto.UserProfileDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testUpdateProfileValidationFailure() throws Exception {
        mockMvc.perform(post("/users/profile/update")
                        .param("username", "")
                        .param("fullName", "")
                        .param("age", "-1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("change-info"))
                .andExpect(model().attributeHasFieldErrors("profileData", "username", "fullName", "age"));
    }

}
