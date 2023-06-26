package com.devmountain.noteApp.services;

import com.devmountain.noteApp.dtos.NoteDto;

import java.util.List;

public interface NoteService {
    List<String> updateNote(NoteDto noteDto);

    List<NoteDto> getAllNotes();
    NoteDto getNoteById(Long id);
    NoteDto createNote(NoteDto noteDto);
    NoteDto updateNote(Long id, NoteDto noteDto);
    void deleteNote(Long id);

    List<NoteDto> getAllNotesByUser(Long userId);

    List<String> addNote(Long userId, NoteDto noteDto);

    List<String> deleteNoteById(Long noteId);

    List<String> updateNoteById(Long noteId, NoteDto noteDto);
}
