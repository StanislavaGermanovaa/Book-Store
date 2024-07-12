package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.UserProfileDTO;
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
    private final UserHelperService userHelperService;

    public UserService(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, UserHelperService userHelperService) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userHelperService = userHelperService;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        User user = this.modelMapper.map(userRegisterDTO, User.class);

        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userRepository.save(user);
    }

    public UserProfileDTO getProfileData() {
        return modelMapper.map(userHelperService.getUser(), UserProfileDTO.class);
    }

}