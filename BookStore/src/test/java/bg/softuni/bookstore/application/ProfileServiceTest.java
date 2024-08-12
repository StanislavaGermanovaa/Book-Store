package bg.softuni.bookstore.application;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testGetProfileData() {
        User user = new User();
        UserProfileDTO userProfileDTO = new UserProfileDTO();

        when(mockUserHelperService.getUser()).thenReturn(user);
        when(mockModelMapper.map(user, UserProfileDTO.class)).thenReturn(userProfileDTO);

        UserProfileDTO result = profileService.getProfileData();

        assertEquals(userProfileDTO, result);
        verify(mockUserHelperService).getUser();
        verify(mockModelMapper).map(user, UserProfileDTO.class);
    }

    @Test
    void testUpdateProfile() {
        User user = new User();
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername("newUsername");
        userProfileDTO.setFullName("New FullName");
        userProfileDTO.setAge(30);

        when(mockUserHelperService.getUser()).thenReturn(user);

        profileService.updateProfile(userProfileDTO);

        verify(mockUserHelperService).getUser();
        verify(mockUserRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("newUsername", capturedUser.getUsername());
        assertEquals("New FullName", capturedUser.getFullName());
        assertEquals(30, capturedUser.getAge());
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
