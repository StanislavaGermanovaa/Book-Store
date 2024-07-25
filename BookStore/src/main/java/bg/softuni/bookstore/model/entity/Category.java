package bg.softuni.bookstore.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
public class Category extends BaseEntity{

    @Column(nullable = false,unique = true)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

}
