package bg.softuni.bookstore.application.services;

import bg.softuni.bookstore.model.dto.AddCategoryDTO;
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

    public List<AddCategoryDTO> getAllCategories() {
        return booksRestClient
                .get()
                .uri("http://localhost:8081/categories")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }


    public void deleteCategory(Long id) {
        booksRestClient.delete()
                .uri("http://localhost:8081/categories/{id}", id)
                .retrieve();
    }
}
