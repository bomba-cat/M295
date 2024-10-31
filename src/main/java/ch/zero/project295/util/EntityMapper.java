package ch.zero.project295.util;

import java.util.List;
import java.util.stream.Collectors;

import ch.zero.project295.dto.NoteDTO;
import ch.zero.project295.dto.UserDTO;
import ch.zero.project295.model.Note;
import ch.zero.project295.model.User;

public class EntityMapper {

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static NoteDTO toNoteDTO(Note note) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteId(note.getNoteId());
        noteDTO.setNoteTitle(note.getNoteTitle());
        noteDTO.setNoteBody(note.getNoteBody());
        noteDTO.setCreatedDate(note.getCreatedDate());
        noteDTO.setModifiedDate(note.getModifiedDate());
    
        if (note.getUser() != null) {
            UserDTO userDTO = toUserDTO(note.getUser());
            noteDTO.setUser(userDTO);
        }
    
        return noteDTO;
    }
    

    public static User toUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }


    public static Note toNoteEntity(NoteDTO noteDTO) {
        Note note = new Note();
        note.setNoteId(noteDTO.getNoteId());
        note.setNoteTitle(noteDTO.getNoteTitle());
        note.setNoteBody(noteDTO.getNoteBody());
        note.setCreatedDate(noteDTO.getCreatedDate());
        note.setModifiedDate(noteDTO.getModifiedDate());
        if (noteDTO.getUser() != null) {
            note.setUser(toUserEntity(noteDTO.getUser()));
        }
        return note;
    }
            public static List<NoteDTO> toNoteDTOList(List<Note> users) {
        return users.stream()
                .map(EntityMapper::toNoteDTO)
                .collect(Collectors.toList());
    }
    public static List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream()
                .map(EntityMapper::toUserDTO)
                .collect(Collectors.toList());
        }
}
