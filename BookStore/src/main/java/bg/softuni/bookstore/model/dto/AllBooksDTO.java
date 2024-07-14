package bg.softuni.bookstore.model.dto;

import bg.softuni.bookstore.model.entity.Author;
import bg.softuni.bookstore.model.enums.CategoryType;

public record AllBooksDTO(String title,
                          Author author,
                          CategoryType category,
                          String imageURL,
                          String description,
                          double price) {
}
