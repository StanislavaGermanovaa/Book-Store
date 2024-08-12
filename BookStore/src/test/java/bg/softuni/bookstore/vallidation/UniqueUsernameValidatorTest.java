package bg.softuni.bookstore.vallidation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import bg.softuni.bookstore.application.services.UserService;
import bg.softuni.bookstore.vallidation.validator.UniqueUsernameValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UniqueUsernameValidatorTest {

    @Mock
    private UserService mockUserService;

    private UniqueUsernameValidator uniqueUsernameValidator;

    @Mock
    private ConstraintValidatorContext mockContext;

    @BeforeEach
    void setUp() {
        uniqueUsernameValidator = new UniqueUsernameValidator(mockUserService);
    }

    @Test
    void testIsValidWhenUsernameIsNull() {

        boolean result = uniqueUsernameValidator.isValid(null, mockContext);

        assertTrue(result, "Expected validation to pass for null username");
    }

    @Test
    void testIsValidWhenUsernameIsEmpty() {

        boolean result = uniqueUsernameValidator.isValid("", mockContext);

        assertTrue(result, "Expected validation to pass for empty username");
    }

    @Test
    void testIsValidWhenUsernameIsUnique() {

        String username = "uniqueUser";
        when(mockUserService.isUsernameUnique(username)).thenReturn(true);

        boolean result = uniqueUsernameValidator.isValid(username, mockContext);

        assertTrue(result, "Expected validation to pass for unique username");
    }

    @Test
    void testIsValidWhenUsernameIsNotUnique() {

        String username = "duplicateUser";
        when(mockUserService.isUsernameUnique(username)).thenReturn(false);

        boolean result = uniqueUsernameValidator.isValid(username, mockContext);

        assertFalse(result, "Expected validation to fail for non-unique username");
    }
}