package bg.softuni.bookstore.application;

import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.UserRepository;
import bg.softuni.bookstore.application.services.UserHelperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserHelperServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserDetails mockUserDetails;

    @Mock
    private Authentication mockAuthentication;

    private UserHelperService userHelperService;

    @BeforeEach
    void setUp() {
        userHelperService = new UserHelperService(mockUserRepository);
    }

    @Test
    void testGetUser_UserExists() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(mockUserDetails.getUsername()).thenReturn(username);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(context);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userHelperService.getUser();

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void testGetUserDetails() {
        String username = "testuser";

        when(mockUserDetails.getUsername()).thenReturn(username);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(context);

        UserDetails result = userHelperService.getUserDetails();

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void testGetAuthentication() {
        Authentication auth = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        Authentication result = userHelperService.getAuthentication();

        assertNotNull(result);
        assertEquals(auth, result);
    }
}
