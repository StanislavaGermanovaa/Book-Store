package bg.softuni.bookstore.application;

import bg.softuni.bookstore.application.session.AppUserDetailsService;
import bg.softuni.bookstore.model.entity.Role;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.model.enums.UserRoles;
import bg.softuni.bookstore.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private AppUserDetailsService appUserDetailsService;

    @BeforeEach
    void setUp() {
        appUserDetailsService = new AppUserDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsernameSuccess() {

        Role userRole = new Role();
        userRole.setRole(UserRoles.USER);

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRoles(Set.of(userRole));

        when(mockUserRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        UserDetails userDetails = appUserDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsernameUserNotFound() {
        when(mockUserRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        UsernameNotFoundException thrownException = assertThrows(
                UsernameNotFoundException.class,
                () -> appUserDetailsService.loadUserByUsername("testuser")
        );

        assertEquals("User with username testuser not found", thrownException.getMessage());
    }

    @Test
    void testRoleMapping() {

        Role userRole = new Role();
        userRole.setRole(UserRoles.USER);

        Role adminRole = new Role();
        adminRole.setRole(UserRoles.ADMIN);

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRoles(Set.of(userRole, adminRole));

        when(mockUserRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        UserDetails userDetails = appUserDetailsService.loadUserByUsername("testuser");

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<GrantedAuthority> authorityList = new ArrayList<>(authorities);
        assertTrue(authorityList.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorityList.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
