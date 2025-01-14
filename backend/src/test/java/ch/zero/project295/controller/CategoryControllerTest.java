package ch.zero.project295.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.zero.project295.dto.CategoryDTO;
import ch.zero.project295.model.Category;
import ch.zero.project295.repository.CategoryRepository;
import ch.zero.project295.repository.UserRepository;
import ch.zero.project295.util.EntityMapper;

/**
 * Unit tests for the CategoryController class.
 * <p>
 * These tests validate the API endpoints for creating, retrieving, and deleting categories.
 * </p>
 */
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Test for successfully creating a category.
     * Ensures that a valid category can be created if the associated user exists.
     */
    @Test
    void createCategory_Success() throws Exception {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category");
        categoryDTO.setUserId(1L);

        // Mock the UserRepository to simulate the user exists
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        
        // Mock the CategoryRepository to return the category after saving
        Category category = EntityMapper.toCategoryEntity(categoryDTO);
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        // Act & Assert
        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category created successfully"));
    }

    /**
     * Test for failure in creating a category when the associated user is not found.
     * Ensures that a 404 response is returned if the user does not exist.
     */
    @Test
    void createCategory_Failure_UserNotFound() throws Exception {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category");
        categoryDTO.setUserId(1L);

        // Mock the UserRepository to simulate the user does not exist
        Mockito.when(userRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User with ID 1 not found"));
    }

    /**
     * Test for successfully retrieving a category by ID.
     * Ensures that a valid category is returned if it exists.
     */
    @Test
    void getCategoryById_Success() throws Exception {
        // Arrange
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act & Assert
        mockMvc.perform(get("/category/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category with ID 1 found successfully"))
                .andExpect(jsonPath("$.data.categoryName").value("Test Category"));
    }

    /**
     * Test for failure in retrieving a category by ID when it is not found.
     * Ensures that a 404 response is returned if the category does not exist.
     */
    @Test
    void getCategoryById_Failure_NotFound() throws Exception {
        // Arrange
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/category/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Category with ID 1 not found"));
    }

    /**
     * Test for successfully deleting a category by ID.
     * Ensures that a valid category can be deleted if it exists.
     */
    @Test
    void deleteCategory_Success() throws Exception {
        // Arrange
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act & Assert
        mockMvc.perform(delete("/category/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category with ID 1 deleted successfully"));
    }

    /**
     * Test for failure in deleting a category when it is not found.
     * Ensures that a 404 response is returned if the category does not exist.
     */
    @Test
    void deleteCategory_Failure_NotFound() throws Exception {
        // Arrange
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/category/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Category with ID 1 not found"));
    }
}
