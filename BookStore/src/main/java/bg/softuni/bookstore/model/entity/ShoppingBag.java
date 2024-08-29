package bg.softuni.bookstore.model.entity;


import bg.softuni.bookstore.application.error.OutOfStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class ShoppingBag{
    private List<ShoppingBagItems> shoppingBagItems = new ArrayList<>();

    public void addBook(Book book, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        for (ShoppingBagItems item : shoppingBagItems) {
            if (item.getBook().getId().equals(book.getId())) {
                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity > book.getStock()) {
                    throw new OutOfStockException("Not enough stock available for book ID: " + book.getId());
                }
                item.setQuantity(newQuantity);
                return;
            }
        }

        if (quantity > book.getStock()) {
            throw new OutOfStockException("Not enough stock available for book ID: " + book.getId());
        }

        shoppingBagItems.add(new ShoppingBagItems(book, quantity));
    }

    public void removeBook(Book book, int quantity) {
        for (ShoppingBagItems item : shoppingBagItems) {
            if (item.getBook().getId().equals(book.getId())) {
                int newQuantity = item.getQuantity() - quantity;
                if (newQuantity <= 0) {
                    shoppingBagItems.remove(item);
                } else {
                    item.setQuantity(newQuantity);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Book not found in the shopping bag");
    }

    public double getTotal() {
        return shoppingBagItems.stream().mapToDouble(ShoppingBagItems::getSubtotal).sum();
    }

    public void clear() {
        shoppingBagItems.clear();
    }

}
