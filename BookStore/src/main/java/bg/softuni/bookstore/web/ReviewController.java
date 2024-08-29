package bg.softuni.bookstore.web;

import bg.softuni.bookstore.application.services.ReviewService;
import bg.softuni.bookstore.model.dto.AddReviewDTO;
import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.entity.Review;
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
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/add/{bookId}")
    public String showReviewForm(@PathVariable("bookId") Long bookId, Model model){
        model.addAttribute("bookId", bookId);
        model.addAttribute("reviewDTO", new AddReviewDTO());

////        //TODO fix

        List<AddReviewDTO> reviews = reviewService.getReviewsByBookId(bookId);
        model.addAttribute("allBookReviews", reviews);


        return "book-detail";
    }


    @PostMapping("/add/{bookId}")
    public String addReview(@Valid @ModelAttribute("reviewDTO") AddReviewDTO reviewDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reviewDTO", reviewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reviewDTO", bindingResult);
            return "redirect:/add/" + reviewDTO.getBookId();
        }

        reviewService.save(reviewDTO);
        return "redirect:/book/" + reviewDTO.getBookId();
    }
}
