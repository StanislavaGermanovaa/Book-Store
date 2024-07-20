package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.dto.AuthorDTO;
import bg.softuni.bookstore.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author/{id}")
    public String getAuthorDetails(@PathVariable("id") Long id, Model model) {
        AuthorDTO authorDTO = authorService.getAuthorDetails(id);
        model.addAttribute("authorDetails", authorDTO);
        return "authors"; // Thymeleaf template name
    }
}
