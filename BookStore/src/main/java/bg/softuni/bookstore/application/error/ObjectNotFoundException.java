package bg.softuni.bookstore.application.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
    private final Object id;

    public ObjectNotFoundException(String message, Object id) {
        super(message);
        this.id = id;
    }

}
