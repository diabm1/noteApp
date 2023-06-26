package com.devmountain.noteApp.services;

import com.devmountain.noteApp.dtos.NoteDto;
import com.devmountain.noteApp.entities.Note;
import com.devmountain.noteApp.entities.User;
import com.devmountain.noteApp.repositories.NoteRepository;
import com.devmountain.noteApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<String> updateNote(NoteDto noteDto) {
        List<String> response = new ArrayList<>();
        Optional<Note> optionalNote = noteRepository.findById(noteDto.getId());
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            note.setTitle(noteDto.getTitle());
            note.setContent(noteDto.getContent());
            noteRepository.save(note);
            response.add("Note updated successfully.");
        } else {
            response.add("Note not found.");
        }
        return response;
    }


    @Override
    public List<NoteDto> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        List<NoteDto> noteDtos = new ArrayList<>();
        for (Note note : notes) {
            NoteDto noteDto = new NoteDto(note.getId(), note.getTitle(), note.getContent());
            noteDtos.add(noteDto);
        }
        return noteDtos;
    }


    @Override
    public NoteDto getNoteById(Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            return new NoteDto(note.getId(), note.getTitle(), note.getContent());
        }
        return null; // or throw an exception if desired
    }


    @Override
    public NoteDto createNote(NoteDto noteDto) {
        User user = userRepository.findById(noteDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note.setUser(user);

        Note savedNote = noteRepository.save(note);

        return new NoteDto(savedNote.getId(), savedNote.getTitle(), savedNote.getContent());
    }


    @Override
    public NoteDto updateNote(Long id, NoteDto noteDto) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        existingNote.setTitle(noteDto.getTitle());
        existingNote.setContent(noteDto.getContent());

        Note updatedNote = noteRepository.save(existingNote);

        return new NoteDto(updatedNote.getId(), updatedNote.getTitle(), updatedNote.getContent());
    }


    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }


    @Override
    public List<NoteDto> getAllNotesByUser(Long userId) {
        List<NoteDto> noteDtos = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Note> notes = noteRepository.findAllByUser(user);
            for (Note note : notes) {
                NoteDto noteDto = new NoteDto(note.getId(), note.getTitle(), note.getContent());
                noteDtos.add(noteDto);
            }
        }
        return noteDtos;
    }


    @Override
    public List<String> addNote(Long userId, NoteDto noteDto) {
        List<String> response = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Note note = new Note();
            note.setTitle(noteDto.getTitle());
            note.setContent(noteDto.getContent());
            note.setUser(user);
            noteRepository.save(note);
            response.add("Note added successfully.");
        } else {
            response.add("User not found.");
        }
        return response;
    }

    @Override
    public List<String> deleteNoteById(Long noteId) {
        List<String> response = new ArrayList<>();
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            noteRepository.delete(note);
            response.add("Note deleted successfully.");
        } else {
            response.add("Note not found.");
        }
        return response;
    }

    @Override
    public List<String> updateNoteById(Long noteId, NoteDto noteDto) {
        List<String> response = new ArrayList<>();
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            note.setTitle(noteDto.getTitle());
            note.setContent(noteDto.getContent());
            noteRepository.save(note);
            response.add("Note updated successfully.");
        } else {
            response.add("Note not found.");
        }
        return response;
    }


}
