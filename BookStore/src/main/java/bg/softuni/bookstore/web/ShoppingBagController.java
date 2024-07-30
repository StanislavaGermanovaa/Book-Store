package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.entity.ShoppingBag;
import bg.softuni.bookstore.service.ShoppingBagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;

@Controller
public class ShoppingBagController {

    private final ShoppingBagService shoppingBagService;

    public ShoppingBagController(ShoppingBagService shoppingBagService) {
        this.shoppingBagService = shoppingBagService;
    }

    @GetMapping("/shopping-bag")
    public String viewCart(Model model) {
        ShoppingBag bag = shoppingBagService.getShoppingCart();
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
    public String checkout() {
        // TODO order
        shoppingBagService.clearBag();
        return "redirect:/shopping-bag";
    }
}
