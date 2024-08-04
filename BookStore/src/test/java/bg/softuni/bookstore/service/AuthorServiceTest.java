package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.AuthorDTO;
import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Captor
    private ArgumentCaptor<Author> authorCaptor;
    @Mock
    private AuthorRepository mockAuthorRepository;

    @Mock
    private ModelMapper mockModelMapper;

    private AuthorService testService;

    @BeforeEach
    void setYp(){
        testService=new AuthorService(
                mockAuthorRepository,
                mockModelMapper
        );
    }

    @Test
    void testGetAuthorDetails_AuthorExists() {

        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setName("Author Name");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        author.setBooks(Set.of(book));

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(authorId);
        authorDTO.setName("Author Name");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Book Title");
        authorDTO.setBooks(Set.of(bookDTO));

        when(mockAuthorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(mockModelMapper.map(author, AuthorDTO.class)).thenReturn(authorDTO);
        when(mockModelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        AuthorDTO result = testService.getAuthorDetails(authorId);

        assertNotNull(result);
        assertEquals(authorId, result.getId());
        assertEquals("Author Name", result.getName());
        assertEquals(1, result.getBooks().size());

        verify(mockAuthorRepository, times(1)).findById(authorId);
        verify(mockModelMapper, times(1)).map(author, AuthorDTO.class);
        verify(mockModelMapper, times(1)).map(book, BookDTO.class);
    }

    @Test
    void testGetAuthorDetails_AuthorDoesNotExist() {
        Long authorId = 1L;

        when(mockAuthorRepository.findById(authorId)).thenReturn(Optional.empty());

        AuthorDTO result = testService.getAuthorDetails(authorId);

        assertNull(result);

        verify(mockAuthorRepository, times(1)).findById(authorId);
        verify(mockModelMapper, times(0)).map(any(Author.class), eq(AuthorDTO.class));
        verify(mockModelMapper, times(0)).map(any(Book.class), eq(BookDTO.class));
    }
}
