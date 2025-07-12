package com.example.notesapp.data

import com.example.notesapp.models.Note

class InMemoryNotesSource : NotesSource {
    private val notes = mutableListOf<Note>()

    override fun addNote(note: Note) {
        notes.add(note)
    }

    override fun updateNote(note: Note) {
        val index = notes.indexOfFirst { it.id == note.id }
        if (index != -1) {
            notes[index] = note
        }
    }

    override fun deleteNote(id: String) {
        notes.removeIf { id == it.id }
    }

    override fun getAll(): List<Note> = notes

}