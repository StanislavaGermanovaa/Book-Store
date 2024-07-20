package bg.softuni.bookstore.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class AuthorDTO {
    private Long id;
    private String name;
    private Set<BookDTO> books;
}
