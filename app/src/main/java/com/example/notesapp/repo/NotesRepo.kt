package com.example.notesapp.repo

import com.example.notesapp.data.NotesSource
import com.example.notesapp.models.Note

class NotesRepo(private val notesSource: NotesSource) {

    fun addNote(note: Note) {
        notesSource.addNote(note)
    }

    fun updateNote(note: Note) {
        notesSource.addNote(note)
    }

    fun deleteNote(id: String) {
        notesSource.deleteNote(id)
    }

    fun getAll(): List<Note> = notesSource.getAll()

}