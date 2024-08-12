package bg.softuni.bookstore.model.events;

import bg.softuni.bookstore.model.entity.Book;
import org.springframework.context.ApplicationEvent;

public class BookAddedEvent extends ApplicationEvent {
    private final Book book;

    public BookAddedEvent(Object source, Book book) {
        super(source);
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
