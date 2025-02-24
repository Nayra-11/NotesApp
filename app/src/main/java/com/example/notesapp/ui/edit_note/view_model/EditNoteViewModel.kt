package com.example.notesapp.ui.add_note.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.local.AppDatabase
import com.example.notesapp.data.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    private val _noteOperationStatus = MutableLiveData<NoteOperationStatus>()
    val noteOperationStatus: LiveData<NoteOperationStatus> = _noteOperationStatus

    fun loadNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = AppDatabase.DatabaseBuilder.getInstance(getApplication()).noteDao()
            val loadedNote = noteDao.getNoteById(noteId)
            withContext(Dispatchers.Main) {
                loadedNote?.let {
                    _note.value = it
                }
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = AppDatabase.DatabaseBuilder.getInstance(getApplication()).noteDao()
            noteDao.updateNote(note)
            withContext(Dispatchers.Main) {
                _noteOperationStatus.value = NoteOperationStatus.UpdateSuccess
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = AppDatabase.DatabaseBuilder.getInstance(getApplication()).noteDao()
            noteDao.deleteNote(note)
            withContext(Dispatchers.Main) {
                _noteOperationStatus.value = NoteOperationStatus.DeleteSuccess
            }
        }
    }

    sealed class NoteOperationStatus {
        object UpdateSuccess : NoteOperationStatus()
        object DeleteSuccess : NoteOperationStatus()
    }
}