package ch.zero.project295.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.zero.project295.dto.CategoryDTO;
import ch.zero.project295.repository.CategoryRepository;
import ch.zero.project295.repository.UserRepository;
import ch.zero.project295.util.ApiResponse;
import ch.zero.project295.util.EntityMapper;
import ch.zero.project295.model.Category;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all categories in the system.
     *
     * @return ResponseEntity containing ApiResponse with a list of all categories
     */
    @Operation(summary = "Get all categories", description = "Retrieves all categories in the system")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = EntityMapper.toCategoryDTOList(categoryList);

        ApiResponse<List<CategoryDTO>> response = new ApiResponse<>(true, "Retrieved all categories", categoryDTOList);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return ResponseEntity containing ApiResponse with the category if found, or 404 status if not found
     */
    @Operation(summary = "Get a category by ID", description = "Retrieves a category by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    CategoryDTO categoryDTO = EntityMapper.toCategoryDTO(category);
                    ApiResponse<CategoryDTO> response = new ApiResponse<>(true, "Category with ID " + id + " found successfully", categoryDTO);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Category with ID " + id + " not found", null)));
    }

    /**
     * Creates a new category.
     *
     * @param categoryDTO the category information to create
     * @return ResponseEntity containing ApiResponse with the created category
     */
    @Operation(summary = "Create a new category", description = "Creates a new category with the provided information")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        if (!userRepository.existsById(categoryDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User with ID " + categoryDTO.getUserId() + " not found", null));
        }
        
        Category category = EntityMapper.toCategoryEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = EntityMapper.toCategoryDTO(savedCategory);
        ApiResponse<CategoryDTO> response = new ApiResponse<>(true, "Category created successfully", savedCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates an existing category.
     *
     * @param id the ID of the category to update
     * @param categoryDTO the updated category information
     * @return ResponseEntity containing ApiResponse with the updated category or 404 status if not found
     */
    @Operation(summary = "Update a category", description = "Updates an existing category by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(@PathVariable long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setCategoryName(categoryDTO.getCategoryName());
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    CategoryDTO updatedCategoryDTO = EntityMapper.toCategoryDTO(updatedCategory);
                    ApiResponse<CategoryDTO> response = new ApiResponse<>(true, "Category updated successfully for category with ID " + id, updatedCategoryDTO);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Category with ID " + id + " not found", null)));
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete
     * @return ResponseEntity containing ApiResponse with no content or 404 status if not found
     */
    @Operation(summary = "Delete a category", description = "Deletes a category by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    ApiResponse<Void> response = new ApiResponse<>(true, "Category with ID " + id + " deleted successfully", null);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Category with ID " + id + " not found", null)));
    }
}
