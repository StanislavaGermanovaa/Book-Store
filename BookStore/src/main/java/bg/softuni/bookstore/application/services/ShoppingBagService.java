package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.application.error.ObjectNotFoundException;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.ShoppingBag;
import bg.softuni.bookstore.repo.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class ShoppingBagService {
    @Getter
    private ShoppingBag shoppingBag;
    private final BookRepository bookRepository;

    public ShoppingBagService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void init() {
        shoppingBag = new ShoppingBag();
    }

    public void addBookToBag(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new ObjectNotFoundException("Book not found!",bookId));
        shoppingBag.addBook(book);
    }

    public void removeBookFromBag(Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ObjectNotFoundException("Book not found!",bookId));
        shoppingBag.removeBook(book);
    }
    public void clearBag() {
        shoppingBag.clear();
    }
}
