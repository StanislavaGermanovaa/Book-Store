package bg.softuni.bookstore.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class AddReviewDTO {

    @NotNull
    private Long bookId;

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    private LocalDate reviewDate;

}
