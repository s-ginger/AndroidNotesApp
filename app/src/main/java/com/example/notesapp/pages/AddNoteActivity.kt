package com.example.notesapp.pages

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityNewnoteBinding
import com.example.notesapp.models.Note


class AddNoteActivity : AppCompatActivity() {

    private var _binding: ActivityNewnoteBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("New note exception")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityNewnoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("note", Note::class.java)
        } else {
            intent.getParcelableExtra<Note>("note")
        }

        if (note != null) {
            Log.i("NoteToUpdated", note.Name)
            binding.noteEditTitle.setText(note.Name)
            binding.noteEditText.setText(note.Text)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.register) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            val intent = Intent(this@AddNoteActivity, SecondActivity::class.java)
            textCancel.setOnClickListener {
                intent.putExtra("openFragment", "notes")
                startActivity(intent)
            }

            textSave.setOnClickListener {
                if (note != null) {
                    val newNote = note.copy(
                        Name = noteEditTitle.text.toString(),
                        Text = noteEditText.text.toString()
                    )
                    intent.putExtra("openFragment", "notes")
                    intent.putExtra("note", newNote)
                    intent.putExtra("isUpdate", true)
                    Log.d("NOTE_ID", newNote.id.toString())
                } else {
                    intent.putExtra("noteTitle", noteEditTitle.text.toString())
                    intent.putExtra("noteText", noteEditText.text.toString())
                    intent.putExtra("openFragment", "notes")
                    intent.putExtra("isUpdate", false)
                }
                startActivity(intent)
            }


        }

    }

}