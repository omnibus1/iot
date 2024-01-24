package com.example.iot
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class ApiService {

    fun makePostRequest(url: String, requestBody: String, callback: Callback) {

        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .addHeader("Accept", "application/json")
            .post(requestBody.toRequestBody(mediaType))
            .build()

        client.newCall(request).enqueue(callback)
    }

    fun makeGetRequest(url: String, callback: Callback){

        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .addHeader("Accept", "application/json")
            .get()
            .build()
        client.newCall(request).enqueue(callback)
    }
}