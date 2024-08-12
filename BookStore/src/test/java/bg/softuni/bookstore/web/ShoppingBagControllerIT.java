package bg.softuni.bookstore.web;

import bg.softuni.bookstore.application.services.OrderService;
import bg.softuni.bookstore.application.services.ShoppingBagService;
import bg.softuni.bookstore.application.services.UserService;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.ShoppingBag;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.AuthorRepository;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingBagControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    @WithMockUser(username = "testuser@example.com")
    public void testViewCart() throws Exception {
        Author testAuthor = createTestAuthor();
        Book testBook = createTestBook(testAuthor);
        ShoppingBag testBag = new ShoppingBag();
        testBag.addBook(testBook);

        ResultActions result = mockMvc
                .perform(get("/shopping-bag", testBag)
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("shopping-bag"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("shoppingBag"));
    }



    private Author createTestAuthor() {
        Author author = new Author();
        author.setName("Test Author");
        return authorRepository.save(author);
    }

    private Book createTestBook(Author author) {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setDescription("Test description");
        book.setPrice(19.99);
        book.setStock(5);
        book.setCategory("FANTASY");
        book.setImageURL("https://example.com/image.jpg");
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    private User createTestUser() {
        User user = new User();
        user.setUsername("testuser@example.com");
        user.setPassword("password");
        user.setFullName("Test User");
        user.setAge(30);
        user.setEmail("testuser@example.com");

        return userRepository.save(user);
    }

//    private ShoppingBag createTestShoppingBag() {
//        ShoppingBag bag = new ShoppingBag();
//        bag.addBook(testBook);
//        return bag;
//    }
}


