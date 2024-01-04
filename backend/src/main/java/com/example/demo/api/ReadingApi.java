package com.example.demo.api;


import com.example.demo.Device.Device;
import com.example.demo.Device.DeviceRepository;
import com.example.demo.Device.UserDevices;
import com.example.demo.Reading.Reading;
import com.example.demo.Reading.ReadingRepository;
import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ReadingApi {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ReadingRepository readingRepository;
    @GetMapping("test")
    public String test(){
        return "Test";
    }

    @GetMapping(path = "/readings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserReadings(@RequestParam String username){
        User user = userRepository.getByUsername(username);
        List<Device> userDevices = deviceRepository.getUserDevices(user);
        List<Object> response = new ArrayList<>();
        for(Device device: userDevices){
            Map<Object, Object> deviceData = new LinkedHashMap<>();
            deviceData.put("device_id", device.getDevice_id());
            deviceData.put("device_sn", device.getSerialNumber());
            deviceData.put("readings", readingRepository.getReadingsFromDevice(device));
            response.add(deviceData);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/devices_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserDevicesInfo(@RequestParam String username){
        /* some comment */
        User user = userRepository.getByUsername(username);
        List<Device> userDevices = deviceRepository.getUserDevices(user);
        List<Object> response = new ArrayList<>();
        for(Device device: userDevices){
            Map<Object, Object> deviceData = new LinkedHashMap<>();
            Reading newestReading =  readingRepository.getReadingsFromDevice(device).get(0);
            deviceData.put("device_id", device.getDevice_id());
            deviceData.put("device_sn", device.getSerialNumber());
            deviceData.put("last_reading", newestReading.getReading());
            deviceData.put("last_reading_datetime", newestReading.getDateTime());
            response.add(deviceData);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(path = "/device_readings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReadingsFromDevice(@RequestParam String serial_number){

        Device device = deviceRepository.getBySn(serial_number);
        if(device == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_FOUND);
        }
        List<Reading> deviceReadings = readingRepository.getReadingsFromDevice(device);
        return new ResponseEntity<>(deviceReadings, HttpStatus.OK);
    }
}