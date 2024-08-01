package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.entity.*;
import bg.softuni.bookstore.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(User user, ShoppingBag shoppingBag) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());


        List<Book> books = shoppingBag.getShoppingBagItems().stream()
                .map(ShoppingBagItems::getBook)
                .collect(Collectors.toList());

        order.setBooks(books);

        orderRepository.save(order);
    }
}
