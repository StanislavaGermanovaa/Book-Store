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

import java.lang.reflect.Field;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingBagServiceTest {
    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private ShoppingBag mockShoppingBag;


    private ShoppingBagService testService;

    @BeforeEach
    void setUp() throws Exception {
        testService = new ShoppingBagService(mockBookRepository);
        setPrivateShoppingBagField(testService, mockShoppingBag);
    }

    private void setPrivateShoppingBagField(ShoppingBagService service, ShoppingBag shoppingBag) throws Exception {
        Field field = ShoppingBagService.class.getDeclaredField("shoppingBag");
        field.setAccessible(true);
        field.set(service, shoppingBag);
    }

    @Test
    void testAddBookToBag_BookDoesNotExist() {
        Long bookId = 1L;

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            testService.addBookToBag(bookId, 1);
        });

        verify(mockBookRepository, times(1)).findById(bookId);
        verify(mockShoppingBag, times(0)).addBook(any(Book.class), anyInt());
    }

    @Test
    void testRemoveBookFromBag_BookExists() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Sample Book");
        book.setStock(10);

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));

        testService.addBookToBag(bookId, 1);
        testService.removeBookFromBag(bookId, 1);

        verify(mockShoppingBag, times(1)).removeBook(book, 1);
       }

    @Test
    void testRemoveBookFromBag_BookDoesNotExist() {
        Long bookId = 1L;

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.empty());

        ObjectNotFoundException thrownException = assertThrows(ObjectNotFoundException.class, () -> {
            testService.removeBookFromBag(bookId, 1);
        });

        assertEquals("Book not found!", thrownException.getMessage());

        verify(mockBookRepository, times(1)).findById(bookId);
        verify(mockShoppingBag, times(0)).removeBook(any(Book.class), anyInt());
    }

    @Test
    void testClearBag() {
        testService.clearBag();

        verify(mockShoppingBag, times(1)).clear();
    }
}
