package bg.softuni.bookstore.model.dto;

import bg.softuni.bookstore.model.entity.Category;
import bg.softuni.bookstore.vallidation.annotation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Setter
@Getter
@NoArgsConstructor
public class AddBookDTO {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Author must not be blank")
    private String author;

    @NotBlank(message = "Category must not be blank")
    private String category;

    @URL
    private String imageURL;

    @NotBlank(message = "Description must not be blank")
    private String description;


    @Positive(message = "The price should be positive number ")
    private double price;

    public AddBookDTO(Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
    }

    public static AddBookDTO empty() {
        return new AddBookDTO(null, null, null, null, null, null);
    }
}