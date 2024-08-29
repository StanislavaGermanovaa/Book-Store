package bg.softuni.bookstore.web;

import bg.softuni.bookstore.application.error.ObjectNotFoundException;
import bg.softuni.bookstore.application.error.OutOfStockException;
import bg.softuni.bookstore.application.error.UserNotFoundException;
import bg.softuni.bookstore.model.entity.ShoppingBag;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.application.services.OrderService;
import bg.softuni.bookstore.application.services.ShoppingBagService;
import bg.softuni.bookstore.application.services.UserService;
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
public String addBookToBag(@PathVariable Long bookId, @RequestParam("quantity") int quantity, Model model) {
    try {
        shoppingBagService.addBookToBag(bookId, quantity);
    } catch (OutOfStockException e) {
        model.addAttribute("error", e.getMessage());

        return "redirect:/book-details/" + bookId;
    }
    return "redirect:/shopping-bag";
}

    @PostMapping("/shopping-bag/remove")
    public String removeBookFromBag(@RequestParam("bookId") Long bookId, @RequestParam("quantity") int quantity, Model model) {
        try {
            shoppingBagService.removeBookFromBag(bookId, quantity);
        } catch (Exception e) {
            model.addAttribute("error", "Error removing book from bag: " + e.getMessage());
        }
        return "redirect:/shopping-bag";
    }

    @PostMapping("/shopping-bag/checkout")
    public String checkout(Principal principal) {

            Optional<User> userOptional = getUserFromPrincipal(principal);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                orderService.createOrder(user, shoppingBagService.getShoppingBag());

                shoppingBagService.clearBag();

                return "confirmation";
            } else {
                throw new UserNotFoundException("User not found!");
            }

    }
        private Optional<User> getUserFromPrincipal(Principal principal) {
            return userService.findByUsername(principal.getName());
        }
}
