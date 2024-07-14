package bg.softuni.bookstore.web;

import bg.softuni.bookstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/admin")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("admin");

        modelAndView.addObject("adminData", userService.getProfileData());

        return modelAndView;
    }
}
