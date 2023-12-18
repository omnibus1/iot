package com.example.demo.Reading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/readings")
public class ReadingController {

    @Autowired
    ReadingRepository readingRepository;

    @GetMapping("test")
    public String test(){
//        LocalDateTime datetime = LocalDateTime.now();
//        Reading r = new Reading(67, "SN2115","10.0", datetime);
//        readingRepository.saveReading(r);
        return "2115";
    }
}

//insert into devices(serial_number) values ('SN2115')
//insert into users(password, username) values ('haslo123', 'adam123')
//insert into user_devices(device_id, user_id) values (1, 1)
