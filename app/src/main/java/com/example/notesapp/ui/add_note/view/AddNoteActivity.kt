package com.example.notesapp.ui.add_note.view

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
import com.example.notesapp.data.models.Note
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.ui.add_note.view_model.AddNoteViewModel
import com.google.android.material.snackbar.Snackbar

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private val viewModel: AddNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        applySavedTheme()
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        binding.btAdd.setOnClickListener {
            if (!binding.etTitle.text.isNullOrEmpty() &&
                !binding.etNote.text.isNullOrEmpty()
            ) {
                viewModel.addNote(
                    Note(
                        0,
                        binding.etTitle.text.toString(),
                        binding.etNote.text.toString()
                    )
                )
            }
        }

        viewModel.addNote.observe(this, Observer {
            binding.btAdd.hideKeyboard()
            Snackbar.make(binding.main, R.string.note_added, Snackbar.LENGTH_LONG)
                .setAction(R.string.dismiss) {
                    finish()
                }.show()
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