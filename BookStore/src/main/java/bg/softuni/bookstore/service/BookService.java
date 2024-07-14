package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.AddBookDTO;
import bg.softuni.bookstore.model.dto.AllBooksDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.Category;
import bg.softuni.bookstore.repo.AuthorRepository;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.repo.CategoryRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class BookService {

    private final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private final RestClient booksRestClient;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    public BookService(@Qualifier("booksRestClient") RestClient booksRestClient, AuthorRepository authorRepository, AuthorRepository authorRepository1, CategoryRepository categoryRepository, ModelMapper modelMapper, BookRepository bookRepository) {
        this.booksRestClient = booksRestClient;
        this.authorRepository = authorRepository1;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    public List<AllBooksDTO> getAllBooks() {
        LOGGER.info("Get all books...");

        return booksRestClient
                .get()
                .uri("http://localhost:8080/books")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

//    public void addBook(AddBookDTO addBookDTO) {
//        LOGGER.info("Creating new book...");
//
//        booksRestClient
//                .post()
//                .uri("http://localhost:8080/books")
//                .body(addBookDTO)
//                .retrieve();
//    }

    public void addBook(AddBookDTO addBookDTO) {

        Author author;
        if (addBookDTO.getAuthorId() != null) {
            author = authorRepository.findById(addBookDTO.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid author ID"));
        } else if (addBookDTO.getNewAuthorName() != null && !addBookDTO.getNewAuthorName().isEmpty()) {
            author = new Author();
            author.setName(addBookDTO.getNewAuthorName());
            author = authorRepository.save(author);
        } else {
            throw new IllegalArgumentException("Author information is required");
        }

        Category category = categoryRepository.findByName(addBookDTO.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category"));

        Book book = modelMapper.map(addBookDTO, Book.class);
        book.setAuthor(author);
        book.setCategory(category);

        bookRepository.save(book);
    }
}
