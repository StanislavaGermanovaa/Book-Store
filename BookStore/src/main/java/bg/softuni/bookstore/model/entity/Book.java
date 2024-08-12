package bg.softuni.bookstore.model.entity;

import bg.softuni.bookstore.application.exceptions.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Setter
@Getter
@NoArgsConstructor
public class Book extends BaseEntity{

    @Column(nullable = false,unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    private String category;

    @Column(name = "image_url",nullable = false)
    private String imageURL;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    private int stock;

    public void decreaseStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
        } else {
            throw new OutOfStockException("Not enough stock available.");
        }
    }


}
