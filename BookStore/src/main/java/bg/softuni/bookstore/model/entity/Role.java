package bg.softuni.bookstore.model.entity;

import bg.softuni.bookstore.model.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    public Long getId() {
        return id;
    }

    public Role setId(Long id) {
        this.id = id;
        return this;
    }
    public UserRoles getRole() {
        return role;
    }

    public Role setRole(UserRoles role) {
        this.role = role;
        return this;
    }
}
