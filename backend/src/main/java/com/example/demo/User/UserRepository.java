package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll(){
        return jdbcTemplate.query("SELECT user_id, username, password FROM [dbo].[users]",
                BeanPropertyRowMapper.newInstance(User.class));
    }

    public User getByUsername(String username){
        return jdbcTemplate.queryForObject("SELECT user_id, username, password FROM [dbo].[users] where username = ?",
                BeanPropertyRowMapper.newInstance(User.class), username);

    }

    public int saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        jdbcTemplate.update("INSERT INTO [dbo].[users](username, password) VALUES (?, ?)",
                user.getUsername(), user.getPassword());

        return 1;
    }

    public boolean checkIfUsernameTaken(String username){
        try {
            this.getByUsername(username);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean checkIfUserExistsAndPasswordsMatch(User user){
        try{
            User possibleUser = this.getByUsername(user.getUsername());
            return passwordEncoder.matches(user.getPassword(), possibleUser.getPassword());

        }
        catch (Exception e){
            return false;
        }
    }
}
