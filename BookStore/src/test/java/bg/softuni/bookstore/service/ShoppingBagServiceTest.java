package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.ShoppingBag;
import bg.softuni.bookstore.repo.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingBagServiceTest {

    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    @Mock
    private BookRepository mockBookRepository;

    private ShoppingBagService testService;

    @BeforeEach
    void setUp() {
       testService=new ShoppingBagService(mockBookRepository);
        testService.init();
    }

//    @Test
//    void testAddBookToBag_BookExists() {
//
//        Long bookId = 1L;
//        Book book = new Book();
//        book.setId(bookId);
//        book.setTitle("Sample Book");
//
//        when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));
//
//        testService.addBookToBag(bookId);
//
//        ShoppingBag shoppingBag = testService.getShoppingBag();
//        assertTrue(shoppingBag.getShoppingBagItems().contains(book), "The shopping bag should contain the added book.");
//    }

    @Test
    void testAddBookToBag_BookDoesNotExist() {
        Long bookId = 1L;

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.empty());

        testService.addBookToBag(bookId);

        ShoppingBag shoppingBag = testService.getShoppingBag();
        assertTrue(shoppingBag.getShoppingBagItems().isEmpty(), "The shopping bag should not contain any books.");
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

        testService.removeBookFromBag(bookId);

        ShoppingBag shoppingBag = testService.getShoppingBag();
        assertTrue(shoppingBag.getShoppingBagItems().isEmpty(), "The shopping bag should not contain any books.");
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
