package bg.softuni.bookstore.application.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProfileUpdateException extends RuntimeException {
    public ProfileUpdateException(String message) {
        super(message);
    }
}
