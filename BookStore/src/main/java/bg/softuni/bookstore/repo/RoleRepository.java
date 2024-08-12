package bg.softuni.bookstore.repo;

import bg.softuni.bookstore.model.entity.Role;
import bg.softuni.bookstore.model.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRole(UserRoles role);
}
