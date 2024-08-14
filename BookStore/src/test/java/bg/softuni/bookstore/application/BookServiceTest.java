package bg.softuni.bookstore.application;

import bg.softuni.bookstore.application.error.ObjectNotFoundException;
import bg.softuni.bookstore.application.error.OutOfStockException;
import bg.softuni.bookstore.model.dto.AddBookDTO;
import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.AuthorRepository;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.application.services.BookService;
import bg.softuni.bookstore.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Captor
    private ArgumentCaptor<Book> bookCaptor;
    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private AuthorRepository mockAuthorRepository;

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private ApplicationEventPublisher mockApplicationEventPublisher;

    @Mock
    private UserRepository mockUserRepository;

    private BookService testService;

    private Book book1;
    private Book book2;
    private BookDTO bookDTO1;
    private BookDTO bookDTO2;

    @BeforeEach
    void setUp() {
        testService = new BookService(
                mockAuthorRepository,
                mockModelMapper,
                mockBookRepository,
                mockApplicationEventPublisher,
                mockUserRepository
        );

        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book One");

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book Two");

        bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        bookDTO1.setTitle("Book One");

        bookDTO2 = new BookDTO();
        bookDTO2.setId(2L);
        bookDTO2.setTitle("Book Two");
    }

    @Test
    void testGetAllBooks() {
        when(mockBookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));
        when(mockModelMapper.map(book1, BookDTO.class)).thenReturn(bookDTO1);
        when(mockModelMapper.map(book2, BookDTO.class)).thenReturn(bookDTO2);

        List<BookDTO> result = testService.getAllBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(bookDTO1, result.get(0));
        assertEquals(bookDTO2, result.get(1));

        verify(mockBookRepository, times(1)).findAll();
        verify(mockModelMapper, times(1)).map(book1, BookDTO.class);
        verify(mockModelMapper, times(1)).map(book2, BookDTO.class);
    }

    @Test
    void testAddBook_AuthorExists() {
        AddBookDTO addBookDTO = new AddBookDTO();
        addBookDTO.setTitle("New Book");
        addBookDTO.setAuthor("Existing Author");

        Book book = new Book();
        book.setTitle("New Book");

        Author existingAuthor = new Author();
        existingAuthor.setName("Existing Author");


        when(mockModelMapper.map(addBookDTO, Book.class)).thenReturn(book);
        when(mockAuthorRepository.findAuthorByName(addBookDTO.getAuthor())).thenReturn(existingAuthor);


        testService.addBook(addBookDTO);


        verify(mockBookRepository).save(bookCaptor.capture());
        Book savedBook = bookCaptor.getValue();


        assertNotNull(savedBook);
        assertEquals("New Book", savedBook.getTitle());
        assertEquals("Existing Author", savedBook.getAuthor().getName());


        verify(mockModelMapper, times(1)).map(addBookDTO, Book.class);
        verify(mockAuthorRepository, times(1)).findAuthorByName(addBookDTO.getAuthor());
        verify(mockBookRepository, times(1)).save(book);
    }

    @Test
    void testAddBook_AuthorDoesNotExist() {

        AddBookDTO addBookDTO = new AddBookDTO();
        addBookDTO.setTitle("New Book");
        addBookDTO.setAuthor("New Author");

        Book book = new Book();
        book.setTitle("New Book");

        Author newAuthor = new Author();
        newAuthor.setName("New Author");


        when(mockModelMapper.map(addBookDTO, Book.class)).thenReturn(book);
        when(mockAuthorRepository.findAuthorByName(addBookDTO.getAuthor())).thenReturn(null);
        when(mockAuthorRepository.save(any(Author.class))).thenReturn(newAuthor);


        testService.addBook(addBookDTO);

        verify(mockAuthorRepository).save(any(Author.class));
        verify(mockBookRepository).save(bookCaptor.capture());
        Book savedBook = bookCaptor.getValue();


        assertNotNull(savedBook);
        assertEquals("New Book", savedBook.getTitle());
        assertEquals("New Author", savedBook.getAuthor().getName());

        verify(mockModelMapper, times(1)).map(addBookDTO, Book.class);
        verify(mockAuthorRepository, times(1)).findAuthorByName(addBookDTO.getAuthor());
        verify(mockAuthorRepository, times(1)).save(any(Author.class));
        verify(mockBookRepository, times(1)).save(book);
    }

    @Test
    void testGetBooksDetails_BookExists() {

        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Existing Book");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookId);
        bookDTO.setTitle("Existing Book");

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(mockModelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        BookDTO result = testService.getBooksDetails(bookId);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Existing Book", result.getTitle());

        verify(mockBookRepository, times(1)).findById(bookId);
        verify(mockModelMapper, times(1)).map(book, BookDTO.class);
    }

    @Test
    void testGetBooksDetails_BookDoesNotExist() {
        Long bookId = 1L;

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            testService.getBooksDetails(bookId);
        });

        verify(mockBookRepository, times(1)).findById(bookId);
        verify(mockModelMapper, times(0)).map(any(Book.class), eq(BookDTO.class));
    }



    @Test
    void testDeleteBook_BookExists() {
        Long bookId = 1L;

        Book mockBook = new Book();
        mockBook.setId(bookId);

        User user1 = new User();
        user1.setId(1L);
        user1.getBooks().add(mockBook);

        User user2 = new User();
        user2.setId(2L);
        user2.getBooks().add(mockBook);

        List<User> users = Arrays.asList(user1, user2);

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
        when(mockUserRepository.findAll()).thenReturn(users);

        testService.deleteBook(bookId);

        for (User user : users) {
            assertFalse(user.getBooks().contains(mockBook));
        }

        verify(mockBookRepository, times(1)).findById(bookId);
        verify(mockUserRepository, times(1)).findAll();
        verify(mockUserRepository, times(1)).saveAll(users);
        verify(mockBookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void testRefillStock_BookExists() {
        Long bookId = 1L;
        int amount = 10;

        Book book = new Book();
        book.setId(bookId);
        book.setStock(5);

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.of(book));

        testService.refillStock(bookId, amount);

        verify(mockBookRepository).save(bookCaptor.capture());
        Book savedBook = bookCaptor.getValue();

        assertNotNull(savedBook);
        assertEquals(bookId, savedBook.getId());
        assertEquals(15, savedBook.getStock());

        verify(mockBookRepository, times(1)).findById(bookId);
        verify(mockBookRepository, times(1)).save(book);
    }

    @Test
    void testRefillStock_BookDoesNotExist() {
        Long bookId = 1L;
        int amount = 10;

        when(mockBookRepository.findById(bookId)).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            testService.refillStock(bookId, amount);
        });

        assertEquals("Book not found!", exception.getMessage());

        verify(mockBookRepository, times(1)).findById(bookId);
        verify(mockBookRepository, times(0)).save(any(Book.class));
    }

    @Test
    void testSearchBooks_QueryIsNull() {
        List<Book> result = testService.searchBooks(null);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be an empty list");
    }

    @Test
    void testSearchBooks_QueryIsEmpty() {
        List<Book> result = testService.searchBooks("");

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be an empty list");
    }

    @Test
    void testSearchBooks_QueryIsValid() {

        String query = "Sample";
        Book book1 = new Book();
        book1.setTitle("Sample Book 1");
        Book book2 = new Book();
        book2.setTitle("Another Sample Book");

        when(mockBookRepository.findByTitleContainingIgnoreCase(query)).thenReturn(List.of(book1, book2));

        List<Book> result = testService.searchBooks(query);

        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Result list size should match");
        assertTrue(result.contains(book1), "Result should contain 'Sample Book 1'");
        assertTrue(result.contains(book2), "Result should contain 'Another Sample Book'");
    }

    @Test
    void testGetLowStockBooks_NoBooksBelowThreshold() {
        int threshold = 10;

        when(mockBookRepository.findByStockLessThan(threshold)).thenReturn(List.of());

        List<Book> result = testService.getLowStockBooks(threshold);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be an empty list when no books are below the threshold");
    }

    @Test
    void testGetLowStockBooks_SomeBooksBelowThreshold() {
        int threshold = 10;
        Book book1 = new Book();
        book1.setTitle("Book Below Threshold");
        book1.setStock(5);

        Book book2 = new Book();
        book2.setTitle("Book Above Threshold");
        book2.setStock(15);

        when(mockBookRepository.findByStockLessThan(threshold)).thenReturn(List.of(book1));

        List<Book> result = testService.getLowStockBooks(threshold);

        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Result list size should match");
        assertTrue(result.contains(book1), "Result should contain the book below the threshold");
        assertFalse(result.contains(book2), "Result should not contain the book above the threshold");
    }

    @Test
    void testDecreaseStock_SufficientStock() {
        Book book=new Book();
        book.setStock(10);

        int initialStock = book.getStock();
        int quantityToDecrease = 5;

        book.decreaseStock(quantityToDecrease);

        assertEquals(initialStock - quantityToDecrease, book.getStock());
    }

    @Test
    void testDecreaseStock_InsufficientStock() {
        Book book=new Book();

        int quantityToDecrease = 15;

        OutOfStockException thrown = assertThrows(
                OutOfStockException.class,
                () -> book.decreaseStock(quantityToDecrease),
                "Expected decreaseStock to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Not enough stock available."));
    }

    @Test
    void testDecreaseStock_ExactStock() {
        Book book=new Book();
        book.setStock(10);

        int initialStock = book.getStock();
        int quantityToDecrease = initialStock;

        book.decreaseStock(quantityToDecrease);

        assertEquals(0, book.getStock());
    }
}
