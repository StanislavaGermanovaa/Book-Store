package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.entity.Notification;
import bg.softuni.bookstore.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/users/admin")
    public String getAdminPage(Model model) {
        model.addAttribute("notifications", notificationService.getNotifications());
        return "admin";
    }

    @DeleteMapping("/users/admin/{id}")
    public String deleteNotification(@PathVariable("id") Long id) {
        notificationService.removeNotification(id);
        return "redirect:/users/admin";
    }
}
