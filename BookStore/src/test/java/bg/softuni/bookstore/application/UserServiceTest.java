package bg.softuni.bookstore.application;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import bg.softuni.bookstore.application.error.RoleNotFoundException;
import bg.softuni.bookstore.application.services.UserService;
import bg.softuni.bookstore.model.dto.UserRegisterDTO;
import bg.softuni.bookstore.model.entity.Role;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.RoleRepository;
import bg.softuni.bookstore.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private RoleRepository mockRoleRepository;

    private UserService testService;

    @BeforeEach
    void setUp() {
        testService = new UserService(
                mockModelMapper,
                mockPasswordEncoder,
                mockUserRepository,
                mockRoleRepository
        );
    }

    @Test
    void testRegisterSuccess() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testuser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setEmail("test@example.com");

        User user = new User();
        Role role = new Role();

        when(mockModelMapper.map(userRegisterDTO, User.class)).thenReturn(user);
        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword())).thenReturn("encodedPassword");
        when(mockRoleRepository.findById(2L)).thenReturn(Optional.of(role));

        testService.register(userRegisterDTO);

        verify(mockModelMapper).map(userRegisterDTO, User.class);
        verify(mockPasswordEncoder).encode(userRegisterDTO.getPassword());
        verify(mockRoleRepository).findById(2L);
        verify(mockUserRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("encodedPassword", capturedUser.getPassword());
        assertTrue(capturedUser.getRoles().contains(role));
    }

    @Test
    void testRegisterRoleNotFound() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testuser");
        userRegisterDTO.setPassword("password");

        when(mockModelMapper.map(userRegisterDTO, User.class)).thenReturn(new User());
        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword())).thenReturn("encodedPassword");
        when(mockRoleRepository.findById(2L)).thenReturn(Optional.empty());

        RoleNotFoundException thrownException = assertThrows(RoleNotFoundException.class, () -> {
            testService.register(userRegisterDTO);
        });

        assertEquals("Role not found with ID: 2", thrownException.getMessage());
    }

    @Test
    void testIsUsernameUnique() {
        when(mockUserRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        boolean isUnique = testService.isUsernameUnique("testuser");

        assertTrue(isUnique);
    }

    @Test
    void testIsUsernameNotUnique() {
        when(mockUserRepository.findByUsername("testuser")).thenReturn(Optional.of(new User()));

        boolean isUnique = testService.isUsernameUnique("testuser");

        assertFalse(isUnique);
    }

    @Test
    void testIsEmailUnique() {
        when(mockUserRepository.existsByEmail("test@example.com")).thenReturn(false);

        boolean isUnique = testService.isEmailUnique("test@example.com");

        assertTrue(isUnique);
    }

    @Test
    void testIsEmailNotUnique() {
        when(mockUserRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean isUnique = testService.isEmailUnique("test@example.com");

        assertFalse(isUnique);
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        when(mockUserRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<User> foundUser = testService.findByUsername("testuser");

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    void testFindByUsernameNotFound() {
        when(mockUserRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        Optional<User> foundUser = testService.findByUsername("testuser");

        assertFalse(foundUser.isPresent());
    }
}
