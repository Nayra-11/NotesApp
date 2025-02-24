package com.example.notesapp.ui.main.view_model

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

class MainViewModel(val app: Application) : AndroidViewModel(app) {

    val _notes: MutableLiveData<List<Note>> = MutableLiveData()
    val notes: LiveData<List<Note>> = _notes

    fun getAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = AppDatabase.DatabaseBuilder.getInstance(app.applicationContext).noteDao()

            val result = noteDao.getNotes()
            withContext(Dispatchers.Main){
                _notes.postValue(result)
            }
        }
    }

}