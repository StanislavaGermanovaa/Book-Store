package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.model.entity.Role;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.model.enums.UserRoles;
import bg.softuni.bookstore.repo.RoleRepository;
import bg.softuni.bookstore.repo.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void changeUserRole(Long userId, UserRoles newRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.getRoles().clear();

        Role role = roleRepository.findByRole(newRole);
        if (role != null) {
            user.getRoles().add(role);
        }

        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
