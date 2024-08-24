package bg.softuni.bookstore.web;

import bg.softuni.bookstore.application.services.AdminService;
import bg.softuni.bookstore.model.enums.UserRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        return "users";
    }

    @PostMapping("/admin/users/changeRole/{id}")
    public String changeUserRole(@PathVariable("id") Long userId, @RequestParam("role") String role) {
        adminService.changeUserRole(userId, UserRoles.valueOf(role));
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId) {
        adminService.deleteUser(userId);
        return "redirect:/admin/users";
    }
}
