package bg.softuni.bookstore.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRegisterDTO {
    @NotEmpty
    @Size(min = 3)
    private String username;

    @Email
    private String email;

    @NotEmpty
    @Size(min=5,max = 30)
    private String fullName;

    @NotEmpty
    private Integer age;

    @NotEmpty
    @Size(min = 3,max = 20)
    private String password;

    private String confirmPassword;

   }
