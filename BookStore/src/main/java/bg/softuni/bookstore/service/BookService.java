package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.AddBookDTO;
import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.Category;
import bg.softuni.bookstore.repo.AuthorRepository;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.repo.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private final RestClient booksRestClient;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private CategoryService categoryService;

    public BookService(@Qualifier("booksRestClient") RestClient booksRestClient, AuthorRepository authorRepository, AuthorRepository authorRepository1, CategoryRepository categoryRepository, ModelMapper modelMapper, BookRepository bookRepository) {
        this.booksRestClient = booksRestClient;
        this.authorRepository = authorRepository1;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

//    public Category getCategory(String name) {
//        return booksRestClient
//                .get()
//                .uri("http://localhost:8081/categories/name/{name}", name)
//                .retrieve()
//                .body(Category.class);
//    }

    public String getCategoryName(String name) {
        return String.valueOf(booksRestClient
                .get()
                .uri("http://localhost:8081/categories/name/{name}", name)
                .retrieve()
                .body(Category.class));
    }



    public void addBook(AddBookDTO addBookDTO){

        Book book = modelMapper.map(addBookDTO, Book.class);

        Author author = authorRepository.findAuthorByName(addBookDTO.getAuthor());
        if (author == null) {
            author = new Author();
            author.setName(addBookDTO.getAuthor());
            author = authorRepository.save(author);
        }


        String category = getCategoryName(addBookDTO.getCategory());
        if (category == null) {
            throw new RuntimeException("Category not found: " + addBookDTO.getCategory());
        }

        book.setCategory(category);
        book.setAuthor(author);
        bookRepository.save(book);
    }



    public BookDTO getBooksDetails(Long id) {
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElse(null);
    }

}
