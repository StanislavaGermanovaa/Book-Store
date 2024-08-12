package bg.softuni.bookstore.components;

import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.application.services.BookService;
import bg.softuni.bookstore.application.services.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ScheduledTasks {
    private static final int STOCK_THRESHOLD = 5;
    private final BookService bookService;
    private final NotificationService notificationService;

    public ScheduledTasks(BookService bookService, NotificationService notificationService) {
        this.bookService = bookService;
        this.notificationService = notificationService;
    }

    //@Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    @Scheduled(fixedRate = 60000) //Runs every minute
    public void checkStockLevels() {
        List<Book> lowStockBooks = bookService.getLowStockBooks(STOCK_THRESHOLD);
        for (Book book : lowStockBooks) {
            String message = "The stock for the book '" + book.getTitle() + "' is low. Current stock: " + book.getStock();
            notificationService.addNotification(message);
        }
    }
}
