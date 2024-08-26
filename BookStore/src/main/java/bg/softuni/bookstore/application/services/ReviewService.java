package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.model.dto.AddReviewDTO;
import bg.softuni.bookstore.model.entity.Review;
import bg.softuni.bookstore.repo.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final BookService bookService;

    public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper, BookService bookService) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    public void save(AddReviewDTO reviewDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);

        review.setBook(bookService.findById(reviewDTO.getBookId()));

        reviewRepository.save(review);
    }
}
