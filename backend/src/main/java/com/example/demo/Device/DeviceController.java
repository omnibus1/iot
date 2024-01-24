package com.example.demo.Device;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepository;

    @ResponseBody
    @PostMapping( path = "/add_device", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addDevice(@RequestBody Map<String,String> data){
        String username = data.get("username");
        String serialNumber = data.get("serial_number");
        deviceRepository.bindDeviceToUser(serialNumber, username);
        List<String> response = new ArrayList<>();
        response.add(username);
        response.add(serialNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @ResponseBody
    @GetMapping(path = "/test")
    public String test(){
        return "asd";
    }

}
