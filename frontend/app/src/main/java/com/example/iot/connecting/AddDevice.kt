package com.example.iot.connecting

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.example.iot.R
import java.io.OutputStreamWriter
import java.net.Socket
import java.net.SocketException


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

        val policy = ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

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
            val wifiSSID = getWifiSSID(this)
//            sendDataToDevice()
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

            val message = wifiSSID + "," + wifiPssword + "\n";


            var socket = Socket("192.168.4.22", 8000)

            val outputStream = socket.getOutputStream();
            val writer = OutputStreamWriter(outputStream);

            var ssid = getWifiSSID(this)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear().commit()
            editor.putString("sn", ssid);
            editor.commit()

            try {
                while (true) {
                    writer.write(message);
                    writer.flush();
                }
            } finally {
                socket.close();
            }
        }catch(e: SocketException){
            val intent = Intent(this@AddDevice, ChangeBackWiFi::class.java)
            startActivity(intent);
        }
        catch (e:Exception){
            errorMessage.visibility = View.VISIBLE
        }
    }


    private fun getWifiSSID(context: Context): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                val wifiInfo = wifiManager.connectionInfo
                return wifiInfo.ssid.replace("\"", "") // Remove quotes from SSID
            }
        } else {
            val wifiInfo = wifiManager.connectionInfo
            return wifiInfo.ssid.replace("\"", "") // Remove quotes from SSID
        }

        return "Not connected to WiFi"
    }
}


