package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.UserRegisterDTO;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void register(UserRegisterDTO data) {
        User user=this.modelMapper.map(data, User.class);
        user.setPassword(passwordEncoder.encode(data.getPassword()));

        userRepository.save(user);
    }
}
