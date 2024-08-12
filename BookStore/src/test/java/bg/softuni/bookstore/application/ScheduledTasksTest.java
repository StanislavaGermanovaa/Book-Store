package bg.softuni.bookstore.application;

import bg.softuni.bookstore.application.services.BookService;
import bg.softuni.bookstore.application.services.NotificationService;
import bg.softuni.bookstore.components.ScheduledTasks;
import bg.softuni.bookstore.model.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScheduledTasksTest {

    @Mock
    private BookService mockBookService;

    @Mock
    private NotificationService mockNotificationService;

    private ScheduledTasks scheduledTasks;

    @BeforeEach
    void setUp() {
        scheduledTasks = new ScheduledTasks(mockBookService, mockNotificationService);
    }

    @Test
    void testCheckStockLevels() {
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setStock(4);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setStock(3);

        when(mockBookService.getLowStockBooks(5)).thenReturn(List.of(book1, book2));

        scheduledTasks.checkStockLevels();
        String expectedMessage1 = "The stock for the book 'Book 1' is low. Current stock: 4";
        String expectedMessage2 = "The stock for the book 'Book 2' is low. Current stock: 3";

        verify(mockNotificationService).addNotification(expectedMessage1);
        verify(mockNotificationService).addNotification(expectedMessage2);
    }
}
