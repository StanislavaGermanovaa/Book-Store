package bg.softuni.bookstore.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserProfileDTO {
    private String username;
    private String fullName;
    private int age;

}
