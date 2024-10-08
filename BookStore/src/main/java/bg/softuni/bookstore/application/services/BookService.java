package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.application.error.ObjectNotFoundException;
import bg.softuni.bookstore.model.dto.AddBookDTO;
import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.model.events.BookAddedEvent;
import bg.softuni.bookstore.repo.AuthorRepository;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    public BookService(AuthorRepository authorRepository1, ModelMapper modelMapper, BookRepository bookRepository, ApplicationEventPublisher eventPublisher, UserRepository userRepository) {
        this.authorRepository = authorRepository1;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    public void addBook(AddBookDTO addBookDTO){

        Book book = modelMapper.map(addBookDTO, Book.class);

        Author author = authorRepository.findAuthorByName(addBookDTO.getAuthor());
        if (author == null) {
            author = new Author();
            author.setName(addBookDTO.getAuthor());
            author = authorRepository.save(author);
        }
        book.setAuthor(author);
        bookRepository.save(book);

        eventPublisher.publishEvent(new BookAddedEvent(this, book));
    }
    public BookDTO getBooksDetails(Long id) {
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(()-> new ObjectNotFoundException("Book not found!",id));
    }

    @Transactional
    public void deleteBook(Long id) {
        Book bookToDelete = bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book not found!", id));

        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.getBooks().remove(bookToDelete);
        }

        userRepository.saveAll(users);

        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String query) {

        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        return bookRepository.findByTitleContainingIgnoreCase(query.trim());
    }

    public void refillStock(Long id, int amount) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setStock(book.getStock() + amount);
            bookRepository.save(book);
        } else {
            throw new ObjectNotFoundException("Book not found!",id);
        }
    }


    public List<Book> getLowStockBooks(int threshold) {
        return bookRepository.findByStockLessThan(threshold);
    }

    public Book findById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

}
