package bg.softuni.bookstore.repo;

import bg.softuni.bookstore.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByBookId(Long bookId);
}
