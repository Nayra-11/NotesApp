package com.example.notesapp.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.notesapp.data.models.Note
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.ui.add_note.view.AddNoteActivity
import com.example.notesapp.ui.main.adapter.NotesAdapter
import com.example.notesapp.ui.main.view_model.MainViewModel

class MainActivity : ComponentActivity() {

    lateinit var binding: ActivityMainBinding
    val notesList: MutableList<Note> = mutableListOf()
    lateinit var notesAdapter: NotesAdapter

    val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


//        val notesList = mutableListOf(
//            Note(
//                "Product Meeting",
//                "Review of previous action items, product development update, user feedback and customer insights, competitive analysis, roadmap discussion."
//            ),
//            Note(
//                "To-do list",
//                "Reply to emails, prepare presentation slides for the marketing meeting, conduct research on competitor products, schedule and plan customer interviews, take a break and recharge."
//            ),
//            Note("Shopping list", "Rice, pasta, cereal, yogurt, cheese, butter."),
//            Note(
//                "Project Update",
//                "Submit the project report, discuss findings with the team, plan next steps."
//            ),
//            Note(
//                "Lecture Notes",
//                "Introduction to Android development, basics of Kotlin, understanding layouts, handling user inputs."
//            ),
//            Note("Workout Plan", "Warm-up, cardio exercises, strength training, cool down."),
//            Note("Weekend Plans", "Go hiking, visit the museum, dinner with friends."),
//            Note(
//                "Reading List",
//                "Complete 'Clean Code', start 'Effective Java', read articles on design patterns."
//            ),
//            Note(
//                "Meal Prep",
//                "Prepare meals for the week, grocery shopping for fresh produce, cook and store meals."
//            ),
//            Note("Travel Itinerary", "Book flights, reserve hotel, plan daily activities."),
//            Note(
//                "Product Meeting",
//                "Review of previous action items, product development update, user feedback and customer insights, competitive analysis, roadmap discussion."
//            ),
//            Note(
//                "To-do list",
//                "Reply to emails, prepare presentation slides for the marketing meeting, conduct research on competitor products, schedule and plan customer interviews, take a break and recharge."
//            ),
//            Note("Shopping list", "Rice, pasta, cereal, yogurt, cheese, butter.")
//        )



//        lifecycleScope.launch(Dispatchers.IO) {
//            val noteDao = AppDatabase.DatabaseBuilder.getInstance(this@MainActivity).noteDao()
//            //noteDao.addNote(Note(id=0,title = "To-do list", note = "Reply to emails, prepare presentation slides for the marketing meeting, conduct research on competitor products, schedule and plan customer interviews, take a break and recharge."))
//
//            val notes: List<Note> = noteDao.getNotes()
//            withContext(Dispatchers.Main){
//                notesAdapter = NotesAdapter(notes)
//                binding.rvNotes.adapter = notesAdapter
//
//            }
//        }

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllNotes()

        viewModel.notes.observe(this, Observer {
            notesAdapter = NotesAdapter(it)
            for(i in it.indices) println(it.get(i).id)
            binding.rvNotes.adapter = notesAdapter
        })
    }
}
