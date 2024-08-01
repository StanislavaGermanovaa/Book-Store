package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.entity.ShoppingBag;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.service.OrderService;
import bg.softuni.bookstore.service.ShoppingBagService;
import bg.softuni.bookstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.Optional;

@Controller
public class ShoppingBagController {

    private final ShoppingBagService shoppingBagService;
    private final OrderService orderService;
    private final UserService userService;

    public ShoppingBagController(ShoppingBagService shoppingBagService, OrderService orderService, UserService userService) {
        this.shoppingBagService = shoppingBagService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/shopping-bag")
    public String viewCart(Model model) {
        ShoppingBag bag = shoppingBagService.getShoppingBag();
        double total = bag.getTotal();
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedTotal = df.format(total);

        model.addAttribute("shoppingBag", bag);
        model.addAttribute("total", formattedTotal);
        
        return "shopping-bag";
    }

    @PostMapping("/shopping-bag/add/{bookId}")
    public String addBookToBag(@PathVariable Long bookId) {
        shoppingBagService.addBookToBag(bookId);
        return "redirect:/shopping-bag";
    }

    @PostMapping("/remove")
    public String removeBookFromBag(@RequestParam("bookId") Long bookId) {
        shoppingBagService.removeBookFromBag(bookId);
        return "redirect:/shopping-bag";
    }

    @PostMapping("/shopping-bag/checkout")
    public String checkout(Principal principal) {
        try {
            Optional<User> userOptional = getUserFromPrincipal(principal);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                orderService.createOrder(user, shoppingBagService.getShoppingBag());

                shoppingBagService.clearBag();

                return "confirmation";
            } else {
                return "checkout-error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "checkout-error";
        }
    }
        private Optional<User> getUserFromPrincipal(Principal principal) {
            return userService.findByUsername(principal.getName());
        }
}
