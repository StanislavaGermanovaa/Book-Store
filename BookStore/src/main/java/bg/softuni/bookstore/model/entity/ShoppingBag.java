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


@Setter
@Getter
public class ShoppingBag{
    private List<ShoppingBagItems> shoppingBagItems = new ArrayList<>();

    public void addBook(Book book) {
        for (ShoppingBagItems item : shoppingBagItems) {
            if (item.getBook().getId().equals(book.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        shoppingBagItems.add(new ShoppingBagItems(book, 1));
    }

    public void removeBook(Book book) {
        shoppingBagItems.removeIf(item -> item.getBook().getId().equals(book.getId()));
    }

    public double getTotal() {
        return shoppingBagItems.stream().mapToDouble(ShoppingBagItems::getSubtotal).sum();
    }

    public void clear() {
        shoppingBagItems.clear();
    }

}
