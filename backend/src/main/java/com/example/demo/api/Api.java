package com.example.demo.api;
import com.example.demo.Device.Device;
import com.example.demo.Device.DeviceRepository;
import com.example.demo.Reading.Reading;
import com.example.demo.Reading.ReadingRepository;
import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


/* This is a rest controller class for an API, to perform all needed tasks */
/* */

@RestController
@RequestMapping("/api")
public class Api {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ReadingRepository readingRepository;

    /* GET /readings?username */
    /* Returns a list of objects that contains: device_id:int, device_sn:string, readings:List<reading:string, dateTime:string> */
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

    /* GET /devices_info?username */
    /* Returns a list of objects that contains: device_id:int, device_sn:string, last_reading:string. last_reading_datetime:string */
    @GetMapping(path = "/devices_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserDevicesInfo(@RequestParam String username){
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

    /* GET /device_readings?serial_number */
    /* Returns a list of readings from a device in a such form: reading:string, dateTime:string */
    @GetMapping(path = "/device_readings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReadingsFromDevice(@RequestParam String serial_number){

        Device device = deviceRepository.getDeviceBySn(serial_number);
        if(device == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_FOUND);
        }
        List<Reading> deviceReadings = readingRepository.getReadingsFromDevice(device);
        return new ResponseEntity<>(deviceReadings, HttpStatus.OK);
    }

    /* POST /add_device  body:username, serial_number*/
    /* A endpoint to add a device to user devices */
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

    /* POST /reguster body:username:string, password:string */
    /* A endpoint for registering users, checks if the username is not already taken, and then saves the user */
    @ResponseBody
    @PostMapping( path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerUser(@RequestBody User user){
        Map<String, String> map = new HashMap();
        if(userRepository.checkIfUsernameTaken(user.getUsername())){
            map.put("explanation", String.format("username '%s' is already taken", user.getUsername()));
            map.put("status", "failed");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        userRepository.saveUser(user);
        map.put("status", "success");
        map.put("explanation", String.format("created user '%s'", user.getUsername()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /* POST /login body:username:string, password:string */
    /* A login endpoint for users, checks if the password and username match */
    @ResponseBody
    @PostMapping( path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@RequestBody User user){
        Map<String, String> map = new HashMap();
        if(userRepository.checkIfUserExistsAndPasswordsMatch(user)){
            map.put("status", "success");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("status", "failed");
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
