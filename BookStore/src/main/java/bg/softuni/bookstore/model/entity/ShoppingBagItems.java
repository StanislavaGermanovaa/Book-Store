package bg.softuni.bookstore.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingBagItems {
    private Book book;
    private int quantity;

    public ShoppingBagItems(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }
    public double getSubtotal() {
        return book.getPrice() * quantity;
    }
}
