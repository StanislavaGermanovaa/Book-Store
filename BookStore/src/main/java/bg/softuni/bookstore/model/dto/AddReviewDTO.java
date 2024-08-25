package bg.softuni.bookstore.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddReviewDTO {
    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

}
