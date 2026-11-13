package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userName = intent.getStringExtra("user_name")?: "Гость"
        binding.LoginLoginText.setText(userName)
        val password = intent.getStringExtra("password")

        binding.LoginLoginButton.setOnClickListener {
           if(binding.LodinPassword.text.toString() == password) {
               val intent = Intent(
                   this,
                   osnova::class.java
               )
               intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                       Intent.FLAG_ACTIVITY_CLEAR_TASK
               startActivity(intent)
               finish()
           }else{
               binding.MistakeText.text = "Логин или пароль неверны!"
           }
        }
        binding.LoginRegisterButton.setOnClickListener {
            val intent = Intent(
                this,
                Register_window::class.java
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }





    }

}