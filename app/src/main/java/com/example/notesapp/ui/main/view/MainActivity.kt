package com.example.notesapp.ui.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.notesapp.data.models.Note
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.ui.add_note.view.AddNoteActivity
import com.example.notesapp.ui.edit_note.view.EditNoteActivity
import com.example.notesapp.ui.main.adapter.NotesAdapter
import com.example.notesapp.ui.main.view_model.MainViewModel

class MainActivity : AppCompatActivity(), NotesAdapter.OnNoteClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.toolbar)
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }


        notesAdapter = NotesAdapter(emptyList(), this)
        binding.rvNotes.adapter = notesAdapter


        viewModel.notes.observe(this, Observer { notes ->
            notesAdapter = NotesAdapter(notes, this)
            binding.rvNotes.adapter = notesAdapter
        })


        viewModel.getAllNotes()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_theme -> {
                toggleTheme()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleTheme() {
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        val newNightMode = if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(newNightMode)

        val sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("NightMode", newNightMode)
            apply()
        }

        recreate()
    }
}