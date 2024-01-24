package com.example.iot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: Button;
    private lateinit var registerButton: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.getToLoginButton);
        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent);
        }

        registerButton = findViewById(R.id.goToRegisterButton);
        registerButton.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent);
        }
    }
}