package in.bushansirgur.expensetrackerapi.service;

import in.bushansirgur.expensetrackerapi.dto.CategoryDTO;

import java.util.List;
/**
 * Service interface for managing the categories
 * @author Bushan SC
 * */
public interface CategoryService {

    /**
     * This is for reading the categories from database
     * @return list
     * */
    List<CategoryDTO> getAllCategories();

    /**
     * This is for creating the new category
     * @param categoryDTO
     * @return CategoryDTO
     * */
    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    /**
     * This is for deleting the category from database
     * @param categoryId
     * */
    void deleteCategory(String categoryId);
}
