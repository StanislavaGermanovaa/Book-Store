package bg.softuni.bookstore.service;

import bg.softuni.bookstore.model.dto.AddCategoryDTO;
import bg.softuni.bookstore.model.entity.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CategoryService {
    private final RestClient booksRestClient;

    public CategoryService(@Qualifier("booksRestClient") RestClient booksRestClient) {
        this.booksRestClient = booksRestClient;
    }

    public void addCategory(AddCategoryDTO addCategoryDTO) {
                booksRestClient
                .post()
                .uri("http://localhost:8081/categories")
                .body(addCategoryDTO)
                .retrieve();
    }

    public AddCategoryDTO getCategoryById(Long id) {
        return booksRestClient
                .get()
                .uri("http://localhost:8081/categories/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(AddCategoryDTO.class);
    }


    public AddCategoryDTO getCategoryByName(Category category) {
        return booksRestClient
                .get()
                .uri("http://localhost:8081/categories/name/{name}",category)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(AddCategoryDTO.class);
    }

    public List<AddCategoryDTO> getAllCategories() {
        return booksRestClient
                .get()
                .uri("http://localhost:8081/categories")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
