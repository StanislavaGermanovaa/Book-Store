package bg.softuni.bookstore.service.session;

import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(this::mapToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    private UserDetails mapToUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(mapToGrantedAuthorities(user))
                .build();
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(User user) {
        return user
                .getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                .collect(Collectors.toUnmodifiableList());
    }
}
