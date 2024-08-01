package bg.softuni.bookstore.service;



import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserHelperService {

    private static final String ROLE_PREFIX = "ROLE_";
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
}