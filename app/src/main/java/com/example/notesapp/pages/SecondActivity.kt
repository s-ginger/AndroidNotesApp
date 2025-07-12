package com.example.notesapp.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.notesapp.R
import com.example.notesapp.data.InMemoryNotesSource
import com.example.notesapp.databinding.ActivitySecondBinding
import com.example.notesapp.models.Note
import com.example.notesapp.repo.NotesRepo
import com.example.notesapp.viewmodels.NotesViewModel
import com.example.notesapp.viewmodels.NotesViewModelFactory


class SecondActivity : AppCompatActivity() {

    private var _binding: ActivitySecondBinding? = null
    private val binding
        get() = _binding ?:  throw IllegalStateException("binding second is null")

    val repo = NotesRepo(InMemoryNotesSource())

    val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.loadNotes()

        ViewCompat.setOnApplyWindowInsetsListener(binding.second) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.addNote(Note(Name = "Сьесть кота", Text =  "Вкусно😊"))
        viewModel.notes.observe(this) { notes ->
            notes.forEach {
                Log.i("NoteSaved", it.toString())
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_noteslist -> {
                    viewModel.loadNotes()
                    openFragment(NotesFragment())
                    true
                }
                R.id.nav_addNote-> {
                    intent = Intent(this@SecondActivity, AddNoteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_Settings -> {
                    true
                }
                else -> false
            }
        }
    }

    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


}