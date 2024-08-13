package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.repo.AuthorRepository;
import bg.softuni.bookstore.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerIT {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAuthorDetails() throws Exception {
        Author testAuthor=createTestAuthor();
        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", testAuthor.getId())
                        .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("authors"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("authorDetails"))
                .andExpect(MockMvcResultMatchers.model().attribute("authorDetails",
                        org.hamcrest.Matchers.hasProperty("name", org.hamcrest.Matchers.equalTo(testAuthor.getName()))))
                .andDo(print());
    }

    private Author createTestAuthor() {
        Author author = new Author();
        author.setName("Test Author");
        return authorRepository.save(author);
    }

}
