package com.example.notesapp.data

import com.example.notesapp.models.Note

interface NotesSource {
    fun addNote(note: Note)
    fun updateNote(note: Note)
    fun getAll(): List<Note>
    fun deleteNote(id: String)
}