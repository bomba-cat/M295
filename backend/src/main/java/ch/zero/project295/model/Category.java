package ch.zero.project295.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a category in the system, which can be used to organize notes.
 * Each category is associated with a specific user.
 */

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
    