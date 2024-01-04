package com.example.demo.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @PostMapping( path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerUser(@RequestBody User user){
        Map<String, String> map = new HashMap();
        if(userRepository.checkIfUsernameTaken(user.getUsername())){
            map.put("explanation", String.format("username '%s' is already taken", user.getUsername()));
            map.put("status", "failed");
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);
        }

        userRepository.saveUser(user);
        map.put("status", "success");
        map.put("explanation", String.format("created user '%s'", user.getUsername()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

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
