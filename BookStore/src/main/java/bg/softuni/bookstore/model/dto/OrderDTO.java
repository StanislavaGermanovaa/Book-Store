package bg.softuni.bookstore.model.dto;

import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private User user;
    private List<Book> books;
    private LocalDate orderDate;


}
