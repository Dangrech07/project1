package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.databinding.ActivityRegisterWindowBinding

class Register_window : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterWindowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.RegisterRegisterButton.setOnClickListener {
            val login = binding.registerLogin.text.toString()
            val pass = binding.registerPassword.text.toString()
            val reppass = binding.registerPasswordRepit.text.toString()
            if (login == "") {
                binding.MistakeText.text = "Логин не может быть пустым!"
            }
            else {
                if (pass == "") {
                    binding.MistakeText.text = "Поле 'Пароль' не может быть пустым!"
                }
                else {
                    if (pass == reppass) {
                        val intent = Intent(
                            this,
                            MainActivity::class.java
                        ).apply {
                            putExtra("user_name", login)
                            putExtra("password", pass)
                        }
                        startActivity(intent)
                    }else {
                        binding.MistakeText.text = "Пароли не совпадают!"
                    }

                }
            }
        }

        binding.RegisterBackButton.setOnClickListener {
           val intent = Intent(
               this,
               MainActivity::class.java
           )
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP)
           startActivity(intent)
        //val ex = StringBuilder()
        }
    }
}




