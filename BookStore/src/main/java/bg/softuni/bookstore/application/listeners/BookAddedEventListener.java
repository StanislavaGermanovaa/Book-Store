package bg.softuni.bookstore.application.listeners;

import bg.softuni.bookstore.application.services.ProfileService;
import bg.softuni.bookstore.application.services.UserService;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.events.BookAddedEvent;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookAddedEventListener {

    private final ProfileService profileService;

    public BookAddedEventListener( ProfileService profileService) {
        this.profileService = profileService;
    }

    @EventListener
    @Transactional
    public void handleBookAddedEvent(BookAddedEvent event) {
        Book newBook = event.getBook();
        profileService.addBookToAllProfiles(newBook);
    }
}