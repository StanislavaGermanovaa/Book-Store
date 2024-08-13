package bg.softuni.bookstore.application;

import bg.softuni.bookstore.application.error.ObjectNotFoundException;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.ShoppingBag;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.application.services.ShoppingBagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingBagServiceTest {

    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private ShoppingBag mockShoppingBag;

    private ShoppingBagService testService;

    @BeforeEach
    void setUp() {
       testService=new ShoppingBagService(mockBookRepository);
        testService.init();
    }


    @Test
    void testAddBookToBag_BookDoesNotExist() {
        Long bookId = 1L;

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            testService.addBookToBag(bookId);
        });

        verify(mockBookRepository, times(1)).findById(bookId);
        verify(mockShoppingBag, times(0)).addBook(any(Book.class));
    }

    @Test
    void testRemoveBookFromBag_BookExists() {

        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Sample Book");

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));

        testService.addBookToBag(bookId);

        testService.removeBookFromBag(bookId);

        ShoppingBag shoppingBag = testService.getShoppingBag();
        assertFalse(shoppingBag.getShoppingBagItems().contains(book), "The shopping bag should not contain the removed book.");
    }

    @Test
    void testRemoveBookFromBag_BookDoesNotExist() {

        Long bookId = 1L;

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.empty());

        ObjectNotFoundException thrownException = assertThrows(ObjectNotFoundException.class, () -> {
            testService.removeBookFromBag(bookId);
        });

        assertEquals("Book not found!", thrownException.getMessage());

        verify(mockBookRepository, times(1)).findById(bookId);
        verify(mockShoppingBag, times(0)).removeBook(any(Book.class));
    }

    @Test
    void testClearBag() {
        // Setup
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Sample Book");

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));

        testService.addBookToBag(bookId);

        testService.clearBag();

        ShoppingBag shoppingBag = testService.getShoppingBag();
        assertTrue(shoppingBag.getShoppingBagItems().isEmpty(), "The shopping bag should be empty after clearing.");
    }
}
