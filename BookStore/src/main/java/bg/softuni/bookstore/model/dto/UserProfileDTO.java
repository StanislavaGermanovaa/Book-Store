package bg.softuni.bookstore.model.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class UserProfileDTO {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @Min(value = 1, message = "Age must be at least 1")
    private Integer age;

    private Set<BookDTO> books;

}
