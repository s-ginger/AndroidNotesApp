package com.example.notesapp.pages

import android.content.Intent
import android.os.Bundle
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

        val titleNote = intent.getStringExtra("noteTitle")
        val textNote = intent.getStringExtra("noteText")


        if (!titleNote.isNullOrBlank() && !textNote.isNullOrBlank()) {
            val note = Note(Name = titleNote, Text = textNote)
            viewModel.addNote(note)
        }



        ViewCompat.setOnApplyWindowInsetsListener(binding.second) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val openFragmentFromIntent = intent.getStringExtra("openFragment")
        openFragmentFromIntent?.let {
            openFragment(NotesFragment())
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_noteslist -> {
                    viewModel.loadNotes()
                    openFragment(NotesFragment())
                    true
                }
                R.id.nav_addNote-> {
                    val intent = Intent(this@SecondActivity, AddNoteActivity::class.java)
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