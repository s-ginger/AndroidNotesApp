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
import com.example.notesapp.data.InSqlNotesSource
import com.example.notesapp.databinding.ActivitySecondBinding
import com.example.notesapp.utils.safeGetParcelable
import com.example.notesapp.repo.NotesRepo
import com.example.notesapp.viewmodels.NotesViewModel
import com.example.notesapp.viewmodels.NotesViewModelFactory
import com.example.notesapp.models.Data
import com.example.notesapp.models.Update
import com.example.notesapp.models.Note

class SecondActivity : AppCompatActivity() {

    private var _binding: ActivitySecondBinding? = null
    private val binding
        get() = _binding ?:  throw IllegalStateException("binding second is null")

    val repo = NotesRepo(InSqlNotesSource(this))

    val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.loadNotes()


        val data = intent.safeGetParcelable<Data>("note")

        if (data != null) {
            when (data) {
                is Update -> {
                    Log.i("UpdateNote", "updates")
                    viewModel.updateNote(data.note)
                }
                is Note -> {
                    Log.i("Note", "add")
                    viewModel.addNote(data)
                }
            }
            intent.removeExtra("note")
            viewModel.loadNotes()
        }


        ViewCompat.setOnApplyWindowInsetsListener(binding.second) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        openFragment(NotesFragment())

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
                    viewModel.loadNotes()
                    openFragment(SettingsFragment())
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