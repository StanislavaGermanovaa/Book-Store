package bg.softuni.bookstore.model.dto;

import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.enums.CategoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BookDTO{
    private Long id;
    private String title;
    private Author authorName;
    private CategoryType category;
    private String imageURL;
    private String description;
    private double price;
}
