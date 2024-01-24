package com.example.demo.EventHandler;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.example.demo.Device.Device;
import com.example.demo.Device.DeviceRepository;
import com.example.demo.Reading.Reading;
import com.example.demo.Reading.ReadingRepository;
import com.nimbusds.jose.shaded.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;



/* This EventHandler class is responsible for handling events coming from the Azure IOT Hub, it saves the incoming
* readings in the database */
@Service
public class EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    ReadingRepository readingRepository;
    @Autowired
    DeviceRepository deviceRepository;
    public void handleEvent(EventGridEvent event){
        if(!event.getEventType().equals("Reading")){
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");



        String eventData = String.valueOf(event.getData()).replace("\\","");

        LocalDateTime postDateTime = LocalDateTime.parse(event.getEventTime().toString().substring(0,22));
//        postDateTime = postDateTime.plusHours(1);
//        String formatted_date = formatter.format(postDateTime);
//        postDateTime = LocalDateTime.parse(formatted_date);
        System.out.println("---------");
        System.out.println(postDateTime);
        System.out.println("---------");
        HashMap<String, String> map = new Gson().fromJson(eventData.substring(1,eventData.length()-1), HashMap.class);

        Device device = deviceRepository.getDeviceBySn(map.get("sn"));
        if(device==null){
            System.out.println("not found serial number: "+ map.get("sn"));
        }
        Reading reading = new Reading(null, device, map.get("value"), postDateTime);
        readingRepository.saveReading(reading);

    }
}
