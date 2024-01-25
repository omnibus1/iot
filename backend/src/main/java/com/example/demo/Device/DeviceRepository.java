package com.example.demo.Device;


import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Lazy
    @Autowired
    UserRepository userRepository;

    public Device getDeviceBySn(String serialNumber){
        try {
            return jdbcTemplate.queryForObject("SELECT device_id, serial_number FROM [dbo].[devices] where serial_number = ?",
                    BeanPropertyRowMapper.newInstance(Device.class), serialNumber);
        }
        catch (Exception e){
            return null;
        }

    }

    public List<Device> getUserDevices(User user){
        return jdbcTemplate.query("SELECT devices.device_id, devices.serial_number FROM users JOIN user_devices ON users.user_id = user_devices.user_id " +
                        "JOIN devices ON user_devices.device_id = devices.device_id where users.user_id = ?",
                BeanPropertyRowMapper.newInstance(Device.class), user.getUser_id());
    }

    public int bindDeviceToUser(String serialNumber, String username){
        if(getDeviceBySn(serialNumber)==null){
            addDevice(serialNumber);
        }
        User user = userRepository.getByUsername(username);
        Device device = getDeviceBySn(serialNumber);
        if(!checkIfUserAlreadyHasDeviceBinded(user, device)){
            addDeviceToUser(device, user);
        }

        return 1;
    }

    public int addDevice(String serialNumber){
        jdbcTemplate.update("INSERT INTO devices(serial_number) VALUES (?)",
                serialNumber);
        return 1;
    }

    public int addDeviceToUser(Device device, User user){
        jdbcTemplate.update("INSERT INTO user_devices(device_id, user_id) VALUES(?,?)",
                device.getDevice_id(), user.getUser_id());
        return 1;
    }

    public boolean checkIfUserAlreadyHasDeviceBinded(User user, Device device){
        try{
            jdbcTemplate.queryForObject("SELECT id, device_id, user_id FROM [dbo].[user_devices] WHERE user_id = ? AND device_id = ?",
                    BeanPropertyRowMapper.newInstance(UserDevices.class), user.getUser_id(), device.getDevice_id());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}
