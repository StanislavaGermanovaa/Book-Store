package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.model.dto.UserProfileDTO;
import bg.softuni.bookstore.model.entity.Book;
import bg.softuni.bookstore.model.entity.User;
import bg.softuni.bookstore.repo.BookRepository;
import bg.softuni.bookstore.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.LogManager;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final UserRepository userRepository;

    public ProfileService(ModelMapper modelMapper, UserHelperService userHelperService, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userHelperService = userHelperService;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserProfileDTO getProfileData() {
        User user = userHelperService.getUser();
        return modelMapper.map(user, UserProfileDTO.class);
    }

    @Transactional
    public void updateProfile(UserProfileDTO userProfileDTO) {
        User user = userHelperService.getUser();
        user.setUsername(userProfileDTO.getUsername());
        user.setFullName(userProfileDTO.getFullName());
        user.setAge(userProfileDTO.getAge());

        userRepository.save(user);
    }

    @Transactional
    public void addBookToAllProfiles(Book book) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.getBooks().add(book);
            userRepository.save(user);
        }
    }
}