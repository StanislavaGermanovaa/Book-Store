package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.dto.OrderDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.Order;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.AuthorRepository;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.repo.OrderRepository;
import bg.softuni.bookstore.repo.UserRepository;
import bg.softuni.bookstore.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testListOrders() throws Exception {
        Order order=createTestOrder();
        ResultActions perform = mockMvc.perform(get("/order")
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andExpect(view().name("order"))
                .andExpect(model().attributeExists("ordersDetails"));
    }


    private Order createTestOrder() {
        User user = createTestUser();
        Book book = createTestBook();

        Order order = new Order();
        order.setUser(user);
        order.setBooks(List.of(book));
        order.setOrderDate(LocalDate.now());
        return orderRepository.save(order);
    }

    private User createTestUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setFullName("Test User");
        user.setAge(30);
        user.setEmail("unique@test.com");
        return userRepository.save(user);
    }

    private Book createTestBook() {
        Author author = createTestAuthor();

        Book book = new Book();
        book.setTitle("Test Book");
        book.setDescription("Test Description");
        book.setPrice(10);
        book.setImageURL("https://example.com/test-book.jpg");
        book.setCategory("FANTASY");
        book.setStock(5);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    private Author createTestAuthor() {
        Author author = new Author();
        author.setName("Test Author");
        return authorRepository.save(author);
    }

}
