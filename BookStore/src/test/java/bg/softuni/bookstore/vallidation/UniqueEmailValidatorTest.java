package bg.softuni.bookstore.vallidation;


import bg.softuni.bookstore.application.services.UserService;
import bg.softuni.bookstore.vallidation.validator.UniqueEmailValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniqueEmailValidatorTest {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private UniqueEmailValidator uniqueEmailValidator;

    @Mock
    private ConstraintValidatorContext mockContext;

    @BeforeEach
    void setUp() {
        uniqueEmailValidator = new UniqueEmailValidator(mockUserService);
    }

    @Test
    void testIsValidWhenEmailIsNull() {
        boolean result = uniqueEmailValidator.isValid(null, mockContext);

        assertTrue(result, "Expected validation to pass for null email");
    }

    @Test
    void testIsValidWhenEmailIsEmpty() {
        boolean result = uniqueEmailValidator.isValid("", mockContext);

        assertTrue(result, "Expected validation to pass for empty email");
    }

    @Test
    void testIsValidWhenEmailIsUnique() {

        String email = "unique@example.com";
        when(mockUserService.isEmailUnique(email)).thenReturn(true);

        boolean result = uniqueEmailValidator.isValid(email, mockContext);

        assertTrue(result, "Expected validation to pass for unique email");
    }

    @Test
    void testIsValidWhenEmailIsNotUnique() {

        String email = "duplicate@example.com";
        when(mockUserService.isEmailUnique(email)).thenReturn(false);

        boolean result = uniqueEmailValidator.isValid(email, mockContext);

        assertFalse(result, "Expected validation to fail for non-unique email");
    }
}
