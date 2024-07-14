package bg.softuni.bookstore.repo;

import bg.softuni.bookstore.model.entity.Category;
import bg.softuni.bookstore.model.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(CategoryType category);
}
