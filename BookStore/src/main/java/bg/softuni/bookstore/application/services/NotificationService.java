package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.model.entity.Notification;
import bg.softuni.bookstore.repo.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {


    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void addNotification(String message) {
        Optional<Notification> existingNotification = notificationRepository.findByMessage(message);

        if (existingNotification.isEmpty()) {
            Notification notification = new Notification();
            notification.setMessage(message);

            notificationRepository.save(notification);
        }
    }

        public List<Notification> getNotifications() {
            return notificationRepository.findAll();
        }
    
    public void removeNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
