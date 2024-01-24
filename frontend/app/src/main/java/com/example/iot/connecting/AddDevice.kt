package com.example.iot.connecting
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.iot.R
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.Socket


class AddDevice : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    var deviceSSID = "ESP32-Access-Point"
    var devicePassword = "123456789"
    lateinit var editSSID: EditText;
    lateinit var editPassword: EditText
    var deviceConnectionError: Boolean = false
    lateinit var errorMessage: TextView

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        errorMessage = findViewById(R.id.error_message)
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)

        var changeNetwork: Button = findViewById(R.id.change_network);
        editSSID = findViewById(R.id.editSSID);
        editPassword = findViewById(R.id.editPassword);

        changeNetwork.setOnClickListener{
            startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
        }

        var connectToDevice: Button = findViewById(R.id.connect_to_device);
        connectToDevice.setOnClickListener {
            sendDataToDevice()
        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun connectToDevice(){
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val info = wifiManager.connectionInfo
        val ssid = info.ssid
    }

     fun sendDataToDevice(){
        try {
            var wifiSSID = editSSID.text.toString();
            var wifiPssword = editPassword.text.toString();

            val message = wifiSSID + "," + wifiPssword;


            var socket = Socket("192.168.4.22", 8000)

            val outputStream = socket.getOutputStream();
            val writer = OutputStreamWriter(outputStream);

            try {
                while (true) {
                    writer.write(message);
                    writer.flush();
                }
            } finally {
                socket.close();
            }
        }
        catch (e:Exception){
            errorMessage.visibility = View.VISIBLE
        }
    }
}


