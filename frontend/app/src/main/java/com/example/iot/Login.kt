package com.example.iot

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

import android.widget.TextView

import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.content.Intent
import android.content.SharedPreferences
import android.content.Context


class Login : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val editUsername:EditText = findViewById(R.id.editTextUsername);
        val editPassword:EditText = findViewById(R.id.editTextPassword);
        val loginButton:Button = findViewById(R.id.login);
        val apiService = ApiService()

        val errorTag: TextView = findViewById(R.id.errorMessage);


        loginButton.setOnClickListener{
            val username = editUsername.text.toString();
            val password = editPassword.text.toString();
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear().commit()
            editor.putString("username", username);
            editor.commit()
            val intent = Intent(this@Login, HomeScreen::class.java)
            startActivity(intent);

//
//            var body =  JSONObject();
//            body.put("username", username);
//            body.put("password", password);
//
//            apiService.makePostRequest("http://192.168.0.164:8000/users/login", body.toString(), object: Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    errorTag.text = "Internal Error";
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    if (response.code>=400){
//                        errorTag.text = "Error, could not log in"
//                    }
//                    else{
//
//                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                        editor.clear().commit()
//                        editor.putString("username", username);
//                        editor.commit()
//                        val intent = Intent(this@Login, HomeScreen::class.java)
//                        startActivity(intent);
//                    }
//                }
//            })
        }

    }



}