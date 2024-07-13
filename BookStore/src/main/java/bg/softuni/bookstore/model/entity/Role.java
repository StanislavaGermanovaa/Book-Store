package bg.softuni.bookstore.model.entity;

import bg.softuni.bookstore.model.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Setter
@Getter
@NoArgsConstructor
public class Role extends BaseEntity{

    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoles role;

}
