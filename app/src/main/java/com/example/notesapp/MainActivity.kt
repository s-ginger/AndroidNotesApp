package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityRegisterBinding
import com.example.notesapp.models.User
import com.example.notesapp.pages.SecondActivity
import com.example.notesapp.utils.ThemeHelper
import com.example.notesapp.utils.UserHelper


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("binding reg is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        UserHelper.init(this)

        if (UserHelper.user != null) {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("openFragment", "notes")
            startActivity(intent)
        }

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.register) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        with(binding) {
            btnRegister.setOnClickListener {
                val user = User(
                    editName.text.toString(),
                    editEmail.text.toString(),
                    editPassword.text.toString()
                    )
                Log.i("Saved", user.toString())
                UserHelper.user = user
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("openFragment", "notes")
                startActivity(intent)
            }
        }


    }
}
