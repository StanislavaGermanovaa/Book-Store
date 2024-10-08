package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.application.error.ObjectNotFoundException;
import bg.softuni.bookstore.application.error.OutOfStockException;
import bg.softuni.bookstore.model.dto.OrderDTO;
import bg.softuni.bookstore.model.entity.*;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.repo.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    public void createOrder(User user, ShoppingBag shoppingBag) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());


        List<Book> books = shoppingBag.getShoppingBagItems().stream()
                .map(ShoppingBagItems::getBook)
                .collect(Collectors.toList());


        boolean allBooksInStock = shoppingBag.getShoppingBagItems().stream()
                .allMatch(item -> item.getBook().getStock() >= item.getQuantity());

        if (!allBooksInStock) {
            throw new OutOfStockException("One or more books are out of stock.");
        }

        for (ShoppingBagItems item : shoppingBag.getShoppingBagItems()) {
            Book book = item.getBook();
            book.decreaseStock(item.getQuantity());
            bookRepository.save(book);
        }

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
        if(!orderRepository.existsById(id)){
            throw new ObjectNotFoundException("Order not found!",id);
        }
        orderRepository.deleteById(id);
    }

}
