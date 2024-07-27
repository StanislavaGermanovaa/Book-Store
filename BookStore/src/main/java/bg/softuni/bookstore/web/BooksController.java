package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    @GetMapping("/delete-books")
    public String getBooksToDelete(Model model) {

        List<BookDTO> books = booksService.getAllBooks();
        model.addAttribute("books", books);

        return "delete-book";
    }

    @DeleteMapping("book/{id}")
    private String deleteBook(@PathVariable("id") Long id){
        booksService.deleteBook(id);
        return "redirect:/delete-books";
    }
}
