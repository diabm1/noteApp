package com.devmountain.noteApp.controllers;

import com.devmountain.noteApp.dtos.NoteDto;
import com.devmountain.noteApp.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{userId}")
    public List<NoteDto> getAllNotesByUser(@PathVariable Long userId) {
        return noteService.getAllNotesByUser(userId);
    }

    @PostMapping("/{userId}")
    public List<String> addNote(@PathVariable Long userId, @RequestBody NoteDto noteDto) {
        return noteService.addNote(userId, noteDto);
    }

    @DeleteMapping("/{noteId}")
    public List<String> deleteNoteById(@PathVariable Long noteId) {
        return noteService.deleteNoteById(noteId);
    }

    @PutMapping("/{noteId}")
    public List<String> updateNoteById(@PathVariable Long noteId, @RequestBody NoteDto noteDto) {
        return noteService.updateNoteById(noteId, noteDto);
    }

    @GetMapping("/note/{noteId}")
    public NoteDto getNoteById(@PathVariable Long noteId) {
        return noteService.getNoteById(noteId);
    }
}
