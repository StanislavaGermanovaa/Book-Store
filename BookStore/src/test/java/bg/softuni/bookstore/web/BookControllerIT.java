package bg.softuni.bookstore.web;

import bg.softuni.bookstore.application.services.BookService;
import bg.softuni.bookstore.model.dto.AddBookDTO;
import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.repo.AuthorRepository;
import bg.softuni.bookstore.repo.BookRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIT {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testBookDetails() throws Exception {
        Author author = createTestAuthor();
        Book book = createTestBook(author);

        ResultActions result = mockMvc
                .perform(get("/book/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(view().name("book-detail"))
                .andExpect(model().attributeExists("bookDetails"))
                .andExpect(model().attribute("bookDetails", hasProperty("title", is(book.getTitle()))))
                .andExpect(model().attribute("bookDetails", hasProperty("description", is(book.getDescription()))))
                .andExpect(model().attribute("bookDetails", hasProperty("price", is(book.getPrice()))))
                .andExpect(model().attribute("bookDetails", hasProperty("category", is(book.getCategory()))))
                .andExpect(model().attribute("bookDetails", hasProperty("stock", is(book.getStock()))));
    }


    @Test
    public void testNewBookPage() throws Exception {
        mockMvc.perform(get("/book/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-book"))
                .andExpect(model().attributeExists("addBookDTO"))
                .andExpect(model().attribute("addBookDTO", hasProperty("title", nullValue())));
    }



    private Author createTestAuthor() {
        Author author = new Author();
        author.setName("Test Author");
        return authorRepository.save(author);
    }



    private Book createTestBook(Author author) {
        Book book = new Book();
        book.setDescription("Test description");
        book.setImageURL("https://example.com/image.jpg");
        book.setPrice(10);
        book.setTitle("Test title");
        book.setAuthor(author);
        book.setCategory("FANTASY");
        book.setStock(6);
        return bookRepository.save(book);
    }
}
