package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.UserProfileDTO;
import bg.softuni.bookstore.model.dto.UserRegisterDTO;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private UserHelperService mockUserHelperService;

    private UserService testService;

    @BeforeEach
    void setUp(){
        testService = new UserService(
                mockModelMapper,
                mockPasswordEncoder,
                mockUserRepository,
                mockUserHelperService
        );
    }


    @Test
    void testUserRegistration(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("test");
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setFullName("Test Testov");
        userRegisterDTO.setAge(23);
        userRegisterDTO.setPassword("test");

        User user = new User();
        user.setUsername("test");
        user.setEmail("test@example.com");
        user.setFullName("Test Testov");
        user.setAge(23);

        when(mockModelMapper.map(userRegisterDTO, User.class)).thenReturn(user);

        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword()))
                .thenReturn(userRegisterDTO.getPassword() + userRegisterDTO.getPassword());

        testService.register(userRegisterDTO);
        
        verify(mockUserRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertEquals("test", capturedUser.getUsername());
        assertEquals("test@example.com", capturedUser.getEmail());
        assertEquals("Test Testov", capturedUser.getFullName());
        assertEquals(23, capturedUser.getAge());
        assertEquals("testtest", capturedUser.getPassword());

    }

    @Test
    void testGetProfileData() {

        User mockUser = new User();
        UserProfileDTO mockUserProfileDTO = new UserProfileDTO();

        when(mockUserHelperService.getUser()).thenReturn(mockUser);
        when(mockModelMapper.map(mockUser, UserProfileDTO.class)).thenReturn(mockUserProfileDTO);

        UserProfileDTO result = testService.getProfileData();

        assertEquals(mockUserProfileDTO, result);
    }

    @Test
    void testIsUsernameUnique_WhenUsernameDoesNotExist() {

        String username = "uniqueUser";
        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        boolean result = testService.isUsernameUnique(username);

        assertTrue(result, "Username should be unique");
    }

    @Test
    void testIsUsernameUnique_WhenUsernameExists() {

        String username = "existingUser";
        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        boolean result = testService.isUsernameUnique(username);

        assertFalse(result, "Username should not be unique");
    }

    @Test
    void testIsEmailUnique_WhenEmailDoesNotExist(){
        String email = "uniqueEmail@test.bg";
        when(mockUserRepository.existsByEmail(email)).thenReturn(false);

        boolean result=testService.isEmailUnique(email);

        assertTrue(result, "Email should be unique");
    }

    @Test
    void testIsEmailUnique_WhenEmailExists() {
        String email = "existingEmail@example.com";
        when(mockUserRepository.existsByEmail(email)).thenReturn(true);

        boolean result = testService.isEmailUnique(email);

        assertFalse(result, "Email should not be unique");
    }
}
