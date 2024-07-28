//package bg.softuni.bookstore.web;
//
//import bg.softuni.bookstore.service.NotificationService;
//import bg.softuni.bookstore.service.UserService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//public class AdminController {
//    private final UserService userService;
//    private final NotificationService notificationService;
//
//    public AdminController(UserService userService, NotificationService notificationService) {
//        this.userService = userService;
//        this.notificationService = notificationService;
//    }
////
////    @GetMapping("/users/admin")
////    public ModelAndView home(){
////        ModelAndView modelAndView = new ModelAndView("admin");
////
////        modelAndView.addObject("adminData", userService.getProfileData());
////
////        return modelAndView;
////    }
//
//    @GetMapping("/users/admin")
//    public String getAdminPage(Model model) {
//        model.addAttribute("notifications", notificationService.getNotifications());
//        return "admin";
//    }
//
//}
