package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.ShoppingBag;
import bg.softuni.bookstore.repo.BookRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingBagService {
    private ShoppingBag shoppingBag;
    private final BookRepository bookRepository;

    public ShoppingBagService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void init() {
        shoppingBag = new ShoppingBag();
    }

    public ShoppingBag getShoppingCart() {
        return shoppingBag;
    }

    public void addBookToBag(Long bookId) {
        bookRepository.findById(bookId).ifPresent(book -> shoppingBag.addBook(book));
    }

    public void removeBookFromBag(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            shoppingBag.removeBook(book);
        }
    }
    public void clearBag() {
        shoppingBag.clear();
    }
}
