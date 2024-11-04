package ch.zero.project295.util;

import java.util.List;
import java.util.stream.Collectors;

import ch.zero.project295.dto.CategoryDTO;
import ch.zero.project295.dto.NoteDTO;
import ch.zero.project295.dto.UserDTO;
import ch.zero.project295.model.Category;
import ch.zero.project295.model.Note;
import ch.zero.project295.model.User;

public class EntityMapper {

    // Convert User to UserDTO
    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    // Convert UserDTO to User
    public static User toUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }
    public static User toUserEntityRegistration(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    // Convert Note to NoteDTO
    public static NoteDTO toNoteDTO(Note note) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteId(note.getNoteId());
        noteDTO.setNoteTitle(note.getNoteTitle());
        noteDTO.setNoteBody(note.getNoteBody());
        noteDTO.setCreatedDate(note.getCreatedDate());
        noteDTO.setModifiedDate(note.getModifiedDate());

        // Set only the userId, not the entire User object
        if (note.getUser() != null) {
            noteDTO.setUserId(note.getUser().getUserId());
        }
        if (note.getCategory() != null) {
            noteDTO.setCategoryId(note.getCategory().getCategoryId());
        }
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

    // Convert NoteDTO to Note
    public static Note toNoteEntity(NoteDTO noteDTO) {
        Note note = new Note();
        note.setNoteId(noteDTO.getNoteId());
        note.setNoteTitle(noteDTO.getNoteTitle());
        note.setNoteBody(noteDTO.getNoteBody());
        note.setCreatedDate(noteDTO.getCreatedDate());
        note.setModifiedDate(noteDTO.getModifiedDate());

        // Set only the userId by creating a new User instance with just the userId set
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

    // Convert Category to CategoryDTO
    public static CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());

        // Set only the userId, not the entire User object
        if (category.getUser() != null) {
            categoryDTO.setUserId(category.getUser().getUserId());
        }

        return categoryDTO;
    }

    // Convert CategoryDTO to Category
    public static Category toCategoryEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());

        // Set only the userId by creating a new User instance with just the userId set
        if (categoryDTO.getUserId() != 0) {
            User user = new User();
            user.setUserId(categoryDTO.getUserId());
            category.setUser(user);
        }

        return category;
    }

    // Convert List<Note> to List<NoteDTO>
    public static List<NoteDTO> toNoteDTOList(List<Note> notes) {
        return notes.stream()
                .map(EntityMapper::toNoteDTO)
                .collect(Collectors.toList());
    }

    // Convert List<User> to List<UserDTO>
    public static List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream()
                .map(EntityMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    // Convert List<Category> to List<CategoryDTO>
    public static List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
        return categories.stream()
                .map(EntityMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }
}
