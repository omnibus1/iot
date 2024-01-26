package com.example.demo;

import com.azure.messaging.eventgrid.EventGridEvent;
import com.example.demo.Device.Device;
import com.example.demo.Device.DeviceRepository;
import com.example.demo.EventHandler.EventHandler;
import com.example.demo.Reading.Reading;
import com.example.demo.Reading.ReadingRepository;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@SpringBootApplication
public class Demo1Application {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    EventHandler eventHandler;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandler.class);

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

    @JmsListener(destination = "que2115", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String message) {
        try {
            String[] values = message.split(",");
            StringBuilder result = new StringBuilder();

            for (String value : values) {
                // Convert each value to integer
                int intValue = Integer.parseInt(value.trim());

                // Append the character corresponding to the ASCII code
                result.append((char) intValue);
            }


            Map<String, Object> retMap = new Gson().fromJson(
                    String.valueOf(result), new TypeToken<HashMap<String, Object>>() {}.getType()
            );

            Device device = deviceRepository.getDeviceBySn(retMap.get("sn").toString());
            LocalDateTime currentDateTime = LocalDateTime.now();

            Reading reading = new Reading(null, device, retMap.get("pm25").toString(), currentDateTime);

            readingRepository.saveReading(reading);

            LOGGER.info("Message received: {}", result.toString());
        }
        catch (Exception e){
            LOGGER.info("ERROR");
        }
    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
