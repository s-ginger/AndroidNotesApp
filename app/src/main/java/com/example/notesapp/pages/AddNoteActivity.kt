package com.example.notesapp.pages

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityNewnoteBinding

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

        with(binding) {
            val intent = Intent(this@AddNoteActivity, SecondActivity::class.java)
            textCancel.setOnClickListener {
                startActivity(intent)
            }

            textSave.setOnClickListener {
                intent.putExtra("noteTitle", noteEditTitle.text)
                intent.putExtra("noteText", noteEditText.text)
                startActivity(intent)
            }


        }

    }

}