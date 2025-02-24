package com.example.notesapp.ui.edit_note.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityEditNoteBinding
import com.example.notesapp.ui.add_note.view_model.EditNoteViewModel
import com.google.android.material.snackbar.Snackbar

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private val viewModel: EditNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        applySavedTheme()
        super.onCreate(savedInstanceState)

        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteId = intent.getIntExtra("noteId", -1)
        if (noteId != -1) {
            viewModel.loadNote(noteId)
        }

        viewModel.note.observe(this, Observer { note ->
            if (note != null) {
                binding.etTitle.setText(note.title)
                binding.etNote.setText(note.note)
            }
        })

        binding.btUpdate.setOnClickListener {
            if (!binding.etTitle.text.isNullOrEmpty() && !binding.etNote.text.isNullOrEmpty()) {
                val updatedNote = viewModel.note.value?.copy(
                    title = binding.etTitle.text.toString(),
                    note = binding.etNote.text.toString()
                )
                if (updatedNote != null) {
                    viewModel.updateNote(updatedNote)
                }
            }
        }

        binding.deleteIcon.setOnClickListener {
            viewModel.note.value?.let { note ->
                viewModel.deleteNote(note)
            }
        }

        viewModel.noteOperationStatus.observe(this, Observer { status ->
            when (status) {
                is EditNoteViewModel.NoteOperationStatus.UpdateSuccess -> {
                    binding.btUpdate.hideKeyboard()
                    Snackbar.make(binding.root, R.string.note_updated, Snackbar.LENGTH_LONG)
                        .setAction(R.string.dismiss) { finish() }
                        .show()
                }
                is EditNoteViewModel.NoteOperationStatus.DeleteSuccess -> {
                    Snackbar.make(binding.root, R.string.note_deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.dismiss) { finish() }
                        .show()
                }
                else -> {}
            }
        })
    }

    private fun applySavedTheme() {
        val sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val nightMode = sharedPreferences.getInt("NightMode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}