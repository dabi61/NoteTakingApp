package com.example.notetakingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetakingapp.Reponsitory.NoteRepository
import com.example.notetakingapp.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(
    app : Application,
    private val noteRepository: NoteRepository) : AndroidViewModel(app) {
        fun addNote(note : Note) {
            viewModelScope.launch {
                noteRepository.insertNote(note)
            }
        }
    fun updateNote(note : Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }
    fun deleteNote(note : Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun getAllNote() = noteRepository.getAllNote()
    fun searchNote(query: String?) = noteRepository.searchNote(query)

}