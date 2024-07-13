package bg.softuni.bookstore.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping-bag")
@Setter
@Getter
public class ShoppingBag extends BaseEntity{
    @OneToOne
    private User user;

    @ManyToMany
    private List<Book> books;

    public ShoppingBag() {
        this.books=new ArrayList<>();
    }

}
