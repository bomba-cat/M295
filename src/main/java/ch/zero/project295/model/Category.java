package ch.zero.project295.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @NotNull
    @NotBlank(message = "Category name cannot be blank")
    @Size(max = 50, message = "Title cannot be longer than 50 characters")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters

    /**
     * Gets the unique identifier for the category.
     *
     * @return the category ID
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the unique identifier for the category.
     *
     * @param categoryId the category ID to set
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets the name of the category.
     *
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the name of the category.
     *
     * @param categoryName the category name to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the user associated with the category.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the category.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
    