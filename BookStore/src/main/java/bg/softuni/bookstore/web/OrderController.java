package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.dto.OrderDTO;
import bg.softuni.bookstore.model.entity.Order;
import bg.softuni.bookstore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String listOrders(Model model) {
        List<OrderDTO> orders = orderService.getOrderDetails();
        model.addAttribute("ordersDetails", orders);
        return "order";
    }

    @DeleteMapping("/order/{id}")
    public String deleteOrder(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
        return "redirect:/order";
    }
}
