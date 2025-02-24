package com.example.notesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.data.models.Note

@Dao
interface NoteDao {


        @Query("SELECT * FROM notes WHERE id = :noteId")
        suspend fun getNoteById(noteId: Int): Note?


    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<Note>

    @Insert
    suspend fun addNote(vararg note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}