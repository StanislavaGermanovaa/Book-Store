package bg.softuni.bookstore.model.dto;


import bg.softuni.bookstore.model.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BookDTO{
    private Long id;
    private String title;
    private AuthorDTO author;
    private Category category;
    private String imageURL;
    private String description;
    private double price;
}
