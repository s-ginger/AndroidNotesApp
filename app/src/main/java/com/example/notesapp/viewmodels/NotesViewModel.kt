package com.example.notesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.models.Note
import com.example.notesapp.repo.NotesRepo
import kotlinx.coroutines.*

class NotesViewModel(private val repo: NotesRepo) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>(emptyList())
    val notes: LiveData<List<Note>> = _notes

    fun loadNotes() {
        viewModelScope.launch {
            val data = repo.getAll()
            _notes.postValue(data)
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            repo.addNote(note)
            loadNotes()
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repo.updateNote(note)
            loadNotes()
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            repo.deleteNote(id)
            loadNotes()
        }
    }



}