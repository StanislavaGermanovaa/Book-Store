package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.AuthorDTO;
import bg.softuni.bookstore.model.dto.BookDTO;
import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.repo.AuthorRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final RestClient booksRestClient;

    public AuthorService(@Qualifier("booksRestClient") RestClient booksRestClient,AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.booksRestClient = booksRestClient;
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
                .orElse(null);

//
//        return booksRestClient
//                .get()
//                .uri("http://localhost:8081/authors/{id}", id)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .body(AuthorDTO.class);
//

    }
}
