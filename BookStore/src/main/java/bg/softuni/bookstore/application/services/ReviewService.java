package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.model.dto.AddReviewDTO;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.Review;
import bg.softuni.bookstore.repo.ReviewRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public void save(AddReviewDTO reviewDTO) {
        Book book = bookService.findById(reviewDTO.getBookId());

        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setBook(book);

        book.addReview(review);

        reviewRepository.save(review);
    }

public List<AddReviewDTO> getReviewsByBookId(Long bookId) {
    return reviewRepository.findAllByBookId(bookId).stream()
            .map(review -> {
                AddReviewDTO reviewDTO = new AddReviewDTO();
                reviewDTO.setBookId(review.getBook().getId());
                reviewDTO.setRating(review.getRating());
                reviewDTO.setComment(review.getComment());
                reviewDTO.setReviewDate(review.getReviewDate());
                return reviewDTO;
            })
            .collect(Collectors.toList());
}
}
