package ch.zero.project295.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ch.zero.project295.model.User;

/**
 * Repository interface for accessing User data from the database.
 * <p>
 * This interface extends {@link JpaRepository}, providing CRUD operations
 * and additional JPA-related functionality for the User entity.
 * The generic parameters specify the type of the entity and the type of its ID.
 * </p>
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
