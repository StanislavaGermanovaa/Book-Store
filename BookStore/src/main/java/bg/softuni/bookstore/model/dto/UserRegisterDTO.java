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

    @Size(min = 3,message = "{nav_bar_username_error_message}")
    @UniqueUsername(message = "{nav_bar_username_is_taken_message}")
    private String username;

    @Email(regexp = ".*@.*",message = "{nav_bar_email_error}")
    @UniqueEmail(message = "{nav_bar_email_is_take_message}")
    private String email;


    @Size(min = 5, max = 80,message ="{nav_bar_name_error_message}")
    private String fullName;

    @Min(value = 1,message = "{nav_bar_age_error_message}")
    @Max(value = 90,message = "{nav_bar_age_error_message}")
    private Integer age;

    @Size(min = 3,max = 20,message ="{nav_bar_password_error_message}")
    private String password;

    private String confirmPassword;


   }
