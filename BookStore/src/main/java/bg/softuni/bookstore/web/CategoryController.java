package bg.softuni.bookstore.web;

import bg.softuni.bookstore.model.dto.AddCategoryDTO;
import bg.softuni.bookstore.application.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/add")
    public String newCategory(Model model) {

        if (!model.containsAttribute("addCategoryDTO")) {
            model.addAttribute("addCategoryDTO", AddCategoryDTO.empty());
        }

        return "add-category";
    }

    @PostMapping("/category/add")
    public String createCategory(@Valid AddCategoryDTO addCategoryDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addCategoryDTO", addCategoryDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCategoryDTO", bindingResult);
            return "redirect:/category/add";
        }

        categoryService.addCategory(addCategoryDTO);

        return "redirect:/category/add";

    }

    @GetMapping("/categories/all")
    public String getAllCategories(Model model) {

        model.addAttribute("categoryDetails", categoryService.getAllCategories());
        return "category-details";
    }

    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories/all";
    }

}
