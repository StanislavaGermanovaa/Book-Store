package bg.softuni.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Order extends BaseEntity{

    @ManyToOne
    private User user;

    @OneToMany
    private List<Book> books;

    @Column(name = "order-date")
    private LocalDate orderDate;

    public Order() {
        this.books=new ArrayList<>();
    }

}
