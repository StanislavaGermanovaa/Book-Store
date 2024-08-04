package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.dto.OrderDTO;
import bg.softuni.bookstore.model.entity.*;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.repo.OrderRepository;
import bg.softuni.bookstore.service.exceptions.OutOfStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private BookRepository mockBookRepository;

    private OrderService testService;

    @BeforeEach
    void setUp(){
        testService=new OrderService(
                mockOrderRepository,
                mockModelMapper,
                mockBookRepository
        );
    }

    @Test
    void testGetOrderDetails() {
        // Setup
        Order order = new Order();
        Book book = new Book();
        book.setTitle("Book Title");
        order.setBooks(List.of(book));

        when(mockOrderRepository.findAll()).thenReturn(List.of(order));

        List<OrderDTO> result = testService.getOrderDetails();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;

        testService.deleteOrder(orderId);

        verify(mockOrderRepository, times(1)).deleteById(orderId);
    }
}
