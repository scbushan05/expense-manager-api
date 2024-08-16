package in.bushansirgur.expensetrackerapi.controller;

import in.bushansirgur.expensetrackerapi.dto.CategoryDTO;
import in.bushansirgur.expensetrackerapi.io.CategoryRequest;
import in.bushansirgur.expensetrackerapi.io.CategoryResponse;
import in.bushansirgur.expensetrackerapi.mappers.CategoryMapper;
import in.bushansirgur.expensetrackerapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
/**
 * This controller is for managing the categories
 * @author Bushan SC
 * */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CatgoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    /**
     * API for creating the category
     * @param categoryRequest
     * @return CategoryResponse
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = categoryMapper.mapToCategoryDTO(categoryRequest);
        categoryDTO = categoryService.saveCategory(categoryDTO);
        return categoryMapper.mapToCategoryResponse(categoryDTO);
    }

    /**
     * API for reading the categories
     * @return list
     * */
    @GetMapping
    public List<CategoryResponse> readCategories() {
        List<CategoryDTO> listOfCategories = categoryService.getAllCategories();
        return listOfCategories.stream().map(categoryDTO -> categoryMapper.mapToCategoryResponse(categoryDTO)).collect(Collectors.toList());
    }

    /**
     * API for deleting the category
     * @param categoryId
     *
     * */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
    }

}
