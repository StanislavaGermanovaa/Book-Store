package bg.softuni.bookstore.model.dto;

import bg.softuni.bookstore.vallidation.annotation.UniqueEmail;
import bg.softuni.bookstore.vallidation.annotation.UniqueUsername;
import bg.softuni.bookstore.vallidation.annotation.ValidatePasswords;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@ValidatePasswords
public class UserRegisterDTO {

    @Size(min = 3,message = "Username must be at least 3 symbols!")
    @UniqueUsername(message = "Username is already occupied!")
    private String username;

    @Email(regexp = ".*@.*")
    @UniqueEmail(message = "Email is already occupied!")
    private String email;


    @Size(min = 5, max = 80,message ="Name must be between 5 and 80 characters!")
    private String fullName;

    @Min(1)
    @Max(90)
    private Integer age;

    @Size(min = 3,max = 20,message ="Name must be between 5 and 80 symbols!")
    private String password;

    private String confirmPassword;

   }
