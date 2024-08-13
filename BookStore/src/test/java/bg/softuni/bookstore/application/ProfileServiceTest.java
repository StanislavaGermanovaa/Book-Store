package bg.softuni.bookstore.application;

import bg.softuni.bookstore.application.error.ProfileUpdateException;
import bg.softuni.bookstore.application.error.UserNotFoundException;
import bg.softuni.bookstore.application.services.ProfileService;
import bg.softuni.bookstore.application.services.UserHelperService;
import bg.softuni.bookstore.model.dto.UserProfileDTO;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private UserHelperService mockUserHelperService;

    @Mock
    private UserRepository mockUserRepository;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        profileService = new ProfileService(
                mockModelMapper,
                mockUserHelperService,
                mockUserRepository
        );
    }

    @Test
    void testGetProfileData_UserExists() {
        User user = new User();
        UserProfileDTO userProfileDTO = new UserProfileDTO();

        when(mockUserHelperService.getUser()).thenReturn(user);
        when(mockModelMapper.map(user, UserProfileDTO.class)).thenReturn(userProfileDTO);

        UserProfileDTO result = profileService.getProfileData();

        assertEquals(userProfileDTO, result);
        verify(mockUserHelperService, times(1)).getUser();
        verify(mockModelMapper, times(1)).map(user, UserProfileDTO.class);
    }

    @Test
    void testGetProfileData_UserNotFound() {

        when(mockUserHelperService.getUser()).thenReturn(null);

        UserNotFoundException thrownException = assertThrows(UserNotFoundException.class, () -> {
            profileService.getProfileData();
        });

        assertEquals("User not found.", thrownException.getMessage());
        verify(mockUserHelperService, times(1)).getUser();
        verify(mockModelMapper, times(0)).map(any(User.class), eq(UserProfileDTO.class));
    }

    @Test
    void testUpdateProfile_Success() {
        User user = new User();
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername("newUsername");
        userProfileDTO.setFullName("New FullName");
        userProfileDTO.setAge(30);

        when(mockUserHelperService.getUser()).thenReturn(user);

        profileService.updateProfile(userProfileDTO);

        verify(mockUserHelperService, times(1)).getUser();
        verify(mockUserRepository, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("newUsername", capturedUser.getUsername());
        assertEquals("New FullName", capturedUser.getFullName());
        assertEquals(30, capturedUser.getAge());
    }

    @Test
    void testUpdateProfile_UserNotFound() {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername("newUsername");
        userProfileDTO.setFullName("New FullName");
        userProfileDTO.setAge(30);

        when(mockUserHelperService.getUser()).thenReturn(null);

        UserNotFoundException thrownException = assertThrows(UserNotFoundException.class, () -> {
            profileService.updateProfile(userProfileDTO);
        });

        assertEquals("User not found.", thrownException.getMessage());
        verify(mockUserHelperService, times(1)).getUser();
        verify(mockUserRepository, times(0)).save(any(User.class));
    }

    @Test
    void testUpdateProfile_FailureDuringSave() {
        User user = new User();
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername("newUsername");
        userProfileDTO.setFullName("New FullName");
        userProfileDTO.setAge(30);

        when(mockUserHelperService.getUser()).thenReturn(user);
        doThrow(new RuntimeException("Database error")).when(mockUserRepository).save(user);

        ProfileUpdateException thrownException = assertThrows(ProfileUpdateException.class, () -> {
            profileService.updateProfile(userProfileDTO);
        });

        assertEquals("Failed to update profile.", thrownException.getMessage());
        verify(mockUserHelperService, times(1)).getUser();
        verify(mockUserRepository, times(1)).save(user);
    }

    @Test
    void testAddBookToAllProfiles() {
        Book book = new Book();
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(mockUserRepository.findAll()).thenReturn(users);

        profileService.addBookToAllProfiles(book);

        verify(mockUserRepository).findAll();

        for (User user : users) {
            assertTrue(user.getBooks().contains(book));
            verify(mockUserRepository).save(user);
        }
    }
}
