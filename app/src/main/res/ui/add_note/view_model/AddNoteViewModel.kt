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

class AddNoteViewModel (val app: Application) : AndroidViewModel(app) {

    val _addNote: MutableLiveData<Unit> = MutableLiveData()
    val addNote: LiveData<Unit> = _addNote

    fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = AppDatabase.DatabaseBuilder.getInstance(app.applicationContext).noteDao()

            val result = noteDao.addNote(note)
            withContext(Dispatchers.Main){
                _addNote.postValue(result)
            }
        }
    }
}