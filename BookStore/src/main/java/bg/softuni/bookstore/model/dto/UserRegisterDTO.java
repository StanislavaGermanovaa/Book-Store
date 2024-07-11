package bg.softuni.bookstore.model.dto;

import jakarta.validation.constraints.*;

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

    public UserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
