package bg.softuni.bookstore.application.services;



import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserHelperService {
    private final UserRepository userRepository;

    public UserHelperService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        return userRepository.findByUsername(getUserDetails().getUsername())
                .orElse(null);
    }
    public UserDetails getUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void changeNameInSecurityContext(User loggedUser, String newUsername){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
         UserDetails updatePrincipal=org.springframework.security.core.userdetails.User
                 .withUsername(newUsername)
                 .password(loggedUser.getPassword())
                 .authorities(authentication.getAuthorities())
                 .build();

        UsernamePasswordAuthenticationToken updateAuth=
                new UsernamePasswordAuthenticationToken(
                        updatePrincipal,
                        authentication.getCredentials(),
                        authentication.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(updateAuth);
    }

}