package com.example.iot.connecting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.Button
import com.example.iot.ApiService
import com.example.iot.HomeScreen
import com.example.iot.R
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ChangeBackWiFi : AppCompatActivity() {
    lateinit var sharedPreferences : SharedPreferences
    lateinit var username : String
    lateinit var sn: String

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_back_wi_fi)

        username = sharedPreferences.getString("username", "").toString()
        sn = sharedPreferences.getString("sn", "").toString()


        var changeNetwork: Button = findViewById(R.id.changeWiFi);
        changeNetwork.setOnClickListener{
            startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
        }

        var finishAddDevice: Button = findViewById(R.id.finishAddDevice);
        finishAddDevice.setOnClickListener{
            var deviceId = sn;
            var apiService = ApiService()

            var body =  JSONObject();
            body.put("username", username);
            body.put("serial_number", deviceId)
            //body.put("password", password);

            apiService.makePostRequest("http://192.168.137.1:8000/api/add_device", body.toString(),object:
                Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    var x =123123;
                }

                override fun onResponse(call: okhttp3.Call, response: Response) {
                    if(response.code>=400){
                        var x =123;
                    }
                    else{
                        var x = 244;
                        val intent = Intent(this@ChangeBackWiFi, HomeScreen::class.java)
                        startActivity(intent);
                    }

                }
            })
        }
    }
}