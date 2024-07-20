package bg.softuni.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Setter
@Getter
public class Author extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    public Author() {
        this.books=new HashSet<>();
    }


}
