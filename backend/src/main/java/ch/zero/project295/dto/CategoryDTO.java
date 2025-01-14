package ch.zero.project295.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents the Data Transfer Object for Category.
 * Holds category information such as the name and associated user identifier,
 * with validation to ensure proper data integrity.
 */
public class CategoryDTO {
    private long categoryId;

    @NotNull(message = "Category name cannot be null")
    @NotBlank(message = "Category name cannot be blank")
    @Size(max = 50, message = "Category name cannot be longer than 50 characters")
    private String categoryName;

    @NotNull(message = "User ID cannot be null")
    private long userId;


    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
