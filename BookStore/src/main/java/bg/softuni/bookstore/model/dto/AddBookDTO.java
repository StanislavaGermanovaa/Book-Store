package bg.softuni.bookstore.model.dto;

import bg.softuni.bookstore.model.enums.CategoryType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddBookDTO {

    @NotNull
    private String title;

    private String author;

    @NotNull
    private CategoryType category;

    private String imageURL;

    @NotNull
    private String description;

    @NotNull
    @Positive
    private double price;

    public AddBookDTO(Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
    }

    public static AddBookDTO empty() {
        return new AddBookDTO(null, null, null, null, null, null);
    }
}