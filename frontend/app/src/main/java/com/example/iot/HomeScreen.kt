package com.example.iot
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.iot.connecting.AddDevice
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class HomeScreen : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var dataList: List<ReadingBasic>
    lateinit var apiService: ApiService
    var data: ArrayList<ReadingBasic> = ArrayList()
    lateinit var listView: ListView
    lateinit var noDevices: TextView
    lateinit var username: String




    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_home_screen)
        var banner:TextView = findViewById(R.id.welcome_banner);
        username = sharedPreferences.getString("username", "").toString()
        noDevices = findViewById(R.id.no_devices);


        var addDeviceButton: Button = findViewById(R.id.add_device);
        addDeviceButton.setOnClickListener{
            val intent = Intent(this@HomeScreen, AddDevice::class.java)
            startActivity(intent);
        }
        val failedNetwork = sharedPreferences.getBoolean("networkFail", false);

        val error:TextView = findViewById(R.id.network_error);
        if(failedNetwork){
            error.text = "Error, could not find device network";
            error.visibility = View.VISIBLE;
            error.postDelayed(Runnable(){error.visibility = View.INVISIBLE;},5000)
        }


        apiService = ApiService()


        var r:ReadingBasic = ReadingBasic(123,"huj","hjuj2","2115")
        data.add(r);
        var r2:ReadingBasic = ReadingBasic(123,"huj2115","hjuj2adasd","2115123")
        data.add(r2)
        runOnUiThread{this.setUpApi()}




        banner.text = banner.text.toString() + username;

    }

    fun setUpApi(){


        this.apiService.makeGetRequest("http://192.168.137.1:8000/api/devices_info?username="+username, object:
            Callback {
            override fun onFailure(call: Call, e: IOException) {
                var x =123123;
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code>=400){
                    var x =123;
                }
                else{
                    var responseBody:String = response.body?.string() ?: ""
                    updateView(responseBody)
                }

            }
        })
    }
    fun updateView(responseBody:String){
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(Runnable{
            val listType = object: TypeToken<List<ReadingBasic>>() {}.type
            dataList = Gson().fromJson<List<ReadingBasic>>(responseBody, listType)

            var readingAdapter = ReadingAdapter(dataList, applicationContext);

            listView = findViewById(R.id.customListView);

            listView.adapter = readingAdapter
            listView.adapter = readingAdapter
            if(dataList.isEmpty()){
                noDevices.visibility = View.VISIBLE;
            }
        })



    }
}