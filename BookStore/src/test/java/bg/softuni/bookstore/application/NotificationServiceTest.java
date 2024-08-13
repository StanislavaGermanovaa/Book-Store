package bg.softuni.bookstore.application;

import bg.softuni.bookstore.application.error.ObjectNotFoundException;
import bg.softuni.bookstore.model.entity.Notification;
import bg.softuni.bookstore.repo.NotificationRepository;
import bg.softuni.bookstore.application.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Captor
    private ArgumentCaptor<Notification> notificationCaptor;

    @Mock
    private NotificationRepository mockNotificationRepository;

    private NotificationService testService;

    @BeforeEach
    void setUp(){
        testService=new NotificationService(mockNotificationRepository);
    }

    @Test
    void testAddNotification_NewNotification() {

        String message = "New Notification";
        when(mockNotificationRepository.findByMessage(message)).thenReturn(Optional.empty());

        testService.addNotification(message);

        verify(mockNotificationRepository).save(notificationCaptor.capture());
        Notification savedNotification = notificationCaptor.getValue();
        assertNotNull(savedNotification);
        assertEquals(message, savedNotification.getMessage());
    }

    @Test
    void testAddNotification_ExistingNotification() {
        String message = "Existing Notification";
        Notification existingNotification = new Notification();
        existingNotification.setMessage(message);
        when(mockNotificationRepository.findByMessage(message)).thenReturn(Optional.of(existingNotification));

        testService.addNotification(message);

        verify(mockNotificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void testGetNotifications() {

        Notification notification1 = new Notification();
        notification1.setMessage("Notification 1");
        Notification notification2 = new Notification();
        notification2.setMessage("Notification 2");
        when(mockNotificationRepository.findAll()).thenReturn(List.of(notification1, notification2));

        List<Notification> notifications = testService.getNotifications();

        assertNotNull(notifications);
        assertEquals(2, notifications.size());
        assertTrue(notifications.contains(notification1));
        assertTrue(notifications.contains(notification2));
    }

    @Test
    void testRemoveNotification_NotificationExists() {
        Long notificationId = 1L;

        when(mockNotificationRepository.existsById(notificationId)).thenReturn(true);

        testService.removeNotification(notificationId);
        verify(mockNotificationRepository, times(1)).deleteById(notificationId);
    }

    @Test
    void testRemoveNotification_NotificationDoesNotExist() {
        Long notificationId = 1L;

        when(mockNotificationRepository.existsById(notificationId)).thenReturn(false);

        assertThrows(ObjectNotFoundException.class, () -> {
            testService.removeNotification(notificationId);
        });

        verify(mockNotificationRepository, times(1)).existsById(notificationId);
        verify(mockNotificationRepository, times(0)).deleteById(notificationId);
    }

}
