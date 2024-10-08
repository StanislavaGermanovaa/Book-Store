package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.application.error.ObjectNotFoundException;
import bg.softuni.bookstore.model.dto.AuthorDTO;
import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.repo.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public AuthorDTO getAuthorDetails(Long id) {
        return authorRepository.findById(id)
                .map(author -> {
                    AuthorDTO authorDTO = modelMapper.map(author, AuthorDTO.class);
                    authorDTO.setBooks(author.getBooks().stream()
                            .map(book -> modelMapper.map(book, BookDTO.class))
                            .collect(Collectors.toSet()));
                    return authorDTO;
                })
                .orElseThrow(()-> new ObjectNotFoundException("Author not found!",id));
    }
}
