package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.dto.AddBookDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.enums.CategoryType;
import bg.softuni.bookstore.service.AuthorService;
import bg.softuni.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;

    public BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ModelAttribute("allBookCategory")
    public CategoryType[] allBookCategory() {
        return CategoryType.values();
    }

    @ModelAttribute("allAuthors")
    public List<Author> allAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/book/add")
    public String newBook(Model model) {

        if (!model.containsAttribute("addBookDTO")) {
            model.addAttribute("addBookDTO", AddBookDTO.empty());
        }

        return "add-book";
    }



    @PostMapping("/book/add")
    public String createBook(@Valid AddBookDTO addBookDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addBookDTO", addBookDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addBookDTO", bindingResult);
            return "redirect:/book/add";
        }

        bookService.addBook(addBookDTO);

        return "redirect:/book/add";

    }

    @GetMapping("/book/{id}")
    public String offerDetails(@PathVariable("id") Long id,
                               Model model) {

        model.addAttribute("bookDetails", bookService.getOfferDetails(id));

        return "book-detail";
    }
}
