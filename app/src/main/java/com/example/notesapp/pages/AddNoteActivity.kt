package com.example.notesapp.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityNewnoteBinding
import com.example.notesapp.models.Data
import com.example.notesapp.models.Note
import com.example.notesapp.models.Update
import com.example.notesapp.utils.safeGetParcelable

class AddNoteActivity : AppCompatActivity() {

    private var _binding: ActivityNewnoteBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("New note exception")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityNewnoteBinding.inflate(layoutInflater)
        setContentView(binding.root)






        ViewCompat.setOnApplyWindowInsetsListener(binding.register) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var data = intent.safeGetParcelable<Data>("note")

        if (data is Update) {
            Log.i("NoteToUpdated", data.note.name)
            binding.noteEditTitle.setText(data.note.name)
            binding.noteEditText.setText(data.note.text)
        }

        with(binding) {
            val intent = Intent(this@AddNoteActivity, SecondActivity::class.java)
            textCancel.setOnClickListener {
                startActivity(intent)
            }

            textSave.setOnClickListener {
                val resultIntent = Intent(this@AddNoteActivity, SecondActivity::class.java)

                when (val d = data) {
                    null -> {
                        val newNote = Note(
                            name = noteEditTitle.text.toString(),
                            text = noteEditText.text.toString()
                        )
                        resultIntent.putExtra("note", newNote) // Note → add
                    }
                    is Update -> {
                        val updatedNote = d.note.copy(
                            name = noteEditTitle.text.toString(),
                            text = noteEditText.text.toString()
                        )
                        resultIntent.putExtra("note", Update(updatedNote)) // Update → update
                    }
                    else -> null
                }

                startActivity(resultIntent)
            }


        }

    }

}