package bg.softuni.bookstore.repo;

import bg.softuni.bookstore.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByStockLessThan(int threshold);

    Optional<Book> findByTitle(String validTitle);
}
