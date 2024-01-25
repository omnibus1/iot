package com.example.iot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import android.content.SharedPreferences
import android.content.Context

class Register : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        setContentView(R.layout.activity_register)

        val editUsername: EditText = findViewById(R.id.editTextUsername);
        val editPassword: EditText = findViewById(R.id.editTextPassword);
        val registerButton: Button = findViewById(R.id.register);

        val apiService = ApiService()

        val errorTag: TextView = findViewById(R.id.errorMessage);


        registerButton.setOnClickListener{
            val username = editUsername.text.toString();
            val password = editPassword.text.toString();

            var body =  JSONObject();
            body.put("username", username);
            body.put("password", password);

            apiService.makePostRequest("http://10.0.2.2:8000/api/register", body.toString(), object:
                Callback {
                override fun onFailure(call: Call, e: IOException) {
                    errorTag.text = "Internal Error";
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code>=400){
                        errorTag.text = "Error, an account with this email already exists"
                    }
                    else{
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("username", username);
                        editor.commit()
                        val intent = Intent(this@Register, HomeScreen::class.java)
                        startActivity(intent);
                    }
                }
            })
        }


    }
}