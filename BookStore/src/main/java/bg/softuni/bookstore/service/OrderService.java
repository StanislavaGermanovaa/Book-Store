package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.dto.OrderDTO;
import bg.softuni.bookstore.model.entity.*;
import bg.softuni.bookstore.repo.OrderRepository;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public void createOrder(User user, ShoppingBag shoppingBag) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());


        List<Book> books = shoppingBag.getShoppingBagItems().stream()
                .map(ShoppingBagItems::getBook)
                .collect(toList());

        order.setBooks(books);

        orderRepository.save(order);
    }

    public List<OrderDTO> getOrderDetails() {

        List<Order> orders = orderRepository.findAll();

        orders.forEach(order -> order.getBooks().size());

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

}
