package bg.softuni.bookstore.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddCategoryDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String category;

    private String description;

    public AddCategoryDTO(Object o, Object o1) {
    }


    public static AddCategoryDTO empty() {
        return new AddCategoryDTO(null,null);
    }
}
