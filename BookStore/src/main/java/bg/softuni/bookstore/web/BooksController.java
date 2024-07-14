package bg.softuni.bookstore.web;

import bg.softuni.bookstore.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BooksController {

    private final BookService booksService;

    public BooksController(BookService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/books/all")
    public String getAllBooks(Model model) {

        model.addAttribute("allBooks", booksService.getAllBooks());
        return "books";
    }
}
