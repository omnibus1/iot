package com.example.demo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


/* This a controller class for user related topics */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

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
