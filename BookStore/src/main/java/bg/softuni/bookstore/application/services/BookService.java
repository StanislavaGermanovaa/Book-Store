package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.application.error.ObjectNotFoundException;
import bg.softuni.bookstore.model.dto.AddBookDTO;
import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.events.BookAddedEvent;
import bg.softuni.bookstore.repo.AuthorRepository;
import bg.softuni.bookstore.repo.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookService(AuthorRepository authorRepository1, ModelMapper modelMapper, BookRepository bookRepository, ApplicationEventPublisher eventPublisher) {
        this.authorRepository = authorRepository1;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
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
        }else {
            throw new ObjectNotFoundException("Author not found!",addBookDTO.getAuthor());
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

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ObjectNotFoundException("Book not found!", id);
        }
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
}
