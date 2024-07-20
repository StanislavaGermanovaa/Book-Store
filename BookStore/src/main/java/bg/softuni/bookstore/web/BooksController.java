package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BooksController {

    private final BookService booksService;

    public BooksController(BookService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {

        List<BookDTO> books = booksService.getAllBooks();
        model.addAttribute("books", books);

        return "books";
    }
}
