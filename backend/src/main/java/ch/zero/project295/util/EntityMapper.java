package ch.zero.project295.util;

import java.util.List;
import java.util.stream.Collectors;

import ch.zero.project295.dto.CategoryDTO;
import ch.zero.project295.dto.NoteDTO;
import ch.zero.project295.dto.UserDTO;
import ch.zero.project295.model.Category;
import ch.zero.project295.model.Note;
import ch.zero.project295.model.User;

/**
 * Utility class for converting entities to DTOs and vice versa.
 * <p>
 * Provides methods to map between entities (User, Note, Category) 
 * and their corresponding DTOs (UserDTO, NoteDTO, CategoryDTO).
 * </p>
 */
public class EntityMapper {

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user the User entity to convert
     * @return a UserDTO representing the User entity
     */
    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    /**
     * Converts a UserDTO to a User entity.
     *
     * @param userDTO the UserDTO to convert
     * @return a User entity representing the UserDTO
     */
    public static User toUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    /**
     * Converts a UserDTO to a User entity for registration purposes, 
     * including password.
     *
     * @param userDTO the UserDTO to convert
     * @return a User entity representing the UserDTO with password
     */
    public static User toUserEntityRegistration(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    /**
     * Converts a Note entity to a NoteDTO.
     *
     * @param note the Note entity to convert
     * @return a NoteDTO representing the Note entity
     */
    public static NoteDTO toNoteDTO(Note note) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteId(note.getNoteId());
        noteDTO.setNoteTitle(note.getNoteTitle());
        noteDTO.setNoteBody(note.getNoteBody());
        noteDTO.setCreatedDate(note.getCreatedDate());
        noteDTO.setModifiedDate(note.getModifiedDate());

        if (note.getUser() != null) {
            noteDTO.setUserId(note.getUser().getUserId());
            System.out.println("User ID set to: " + note.getUser().getUserId());
        }
        if (note.getCategory() != null) {
            noteDTO.setCategoryId(note.getCategory().getCategoryId());
            System.out.println("Category ID set to: " + note.getCategory().getCategoryId());
        }

        return noteDTO;
    }

    /**
     * Converts a NoteDTO to a Note entity.
     *
     * @param noteDTO the NoteDTO to convert
     * @return a Note entity representing the NoteDTO
     */
    public static Note toNoteEntity(NoteDTO noteDTO) {
        Note note = new Note();
        note.setNoteId(noteDTO.getNoteId());
        note.setNoteTitle(noteDTO.getNoteTitle());
        note.setNoteBody(noteDTO.getNoteBody());
        note.setCreatedDate(noteDTO.getCreatedDate());
        note.setModifiedDate(noteDTO.getModifiedDate());

        if (noteDTO.getUserId() != 0) {
            User user = new User();
            user.setUserId(noteDTO.getUserId());
            note.setUser(user);
        }
        if (noteDTO.getCategoryId() != 0) {
            Category category = new Category();
            category.setCategoryId(noteDTO.getCategoryId());
            note.setCategory(category);
        }

        return note;
    }

    /**
     * Converts a Category entity to a CategoryDTO.
     *
     * @param category the Category entity to convert
     * @return a CategoryDTO representing the Category entity
     */
    public static CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());

        if (category.getUser() != null) {
            categoryDTO.setUserId(category.getUser().getUserId());
        }

        return categoryDTO;
    }

    /**
     * Converts a CategoryDTO to a Category entity.
     *
     * @param categoryDTO the CategoryDTO to convert
     * @return a Category entity representing the CategoryDTO
     */
    public static Category toCategoryEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());

        if (categoryDTO.getUserId() != 0) {
            User user = new User();
            user.setUserId(categoryDTO.getUserId());
            category.setUser(user);
        }

        return category;
    }

    /**
     * Converts a list of Note entities to a list of NoteDTOs.
     *
     * @param notes the list of Note entities to convert
     * @return a list of NoteDTOs representing the Note entities
     */
    public static List<NoteDTO> toNoteDTOList(List<Note> notes) {
        return notes.stream()
                .map(EntityMapper::toNoteDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of User entities to a list of UserDTOs.
     *
     * @param users the list of User entities to convert
     * @return a list of UserDTOs representing the User entities
     */
    public static List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream()
                .map(EntityMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of Category entities to a list of CategoryDTOs.
     *
     * @param categories the list of Category entities to convert
     * @return a list of CategoryDTOs representing the Category entities
     */
    public static List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
        return categories.stream()
                .map(EntityMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }
}
