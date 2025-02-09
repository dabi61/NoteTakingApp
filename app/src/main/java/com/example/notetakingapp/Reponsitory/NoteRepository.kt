package com.example.notetakingapp.Reponsitory

import com.example.notetakingapp.database.NoteDatabase
import com.example.notetakingapp.model.Note

class NoteRepository(private val db: NoteDatabase) {
    suspend fun insertNote(note : Note) = db.getNoteDAO().insertNote(note)
    suspend fun updateNote(note : Note) = db.getNoteDAO().updateNote(note)
    suspend fun deleteNote(note : Note) = db.getNoteDAO().deleteNote(note)

    fun getAllNote() = db.getNoteDAO().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDAO().searchNote(query)

}