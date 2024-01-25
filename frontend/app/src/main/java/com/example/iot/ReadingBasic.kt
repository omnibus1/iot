package com.example.iot

data class ReadingBasic(public val device_id:Int,public val device_sn:String,public val last_reading:String,public val last_reading_datetime:String)