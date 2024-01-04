package com.example.demo.Reading;

import com.example.demo.Device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReadingRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Reading getByDeviceIdAndDate(Reading reading){
        int x = 15;
        return (Reading) jdbcTemplate.query("SELECT reading_id, device_id, reading, date_time FROM [dbo].[readings] where device_id = ? and FORMAT (date_time, 'yyyy-MM-ddThh:mm:ss.fff') = ?",
                BeanPropertyRowMapper.newInstance(Reading.class), reading.getDevice().getDevice_id(), reading.getDateTime());
    }

    public List<Reading> getReadingsFromDevice(Device device){
        return jdbcTemplate.query("select * from readings where device_id = ? order by date_time DESC",
                BeanPropertyRowMapper.newInstance(Reading.class), device.getDevice_id());
    }
    public boolean readingAlreadyExists(Reading reading){
        try{
            this.getByDeviceIdAndDate(reading);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void saveReading(Reading reading){
        jdbcTemplate.update("INSERT INTO readings(device_id, reading, date_time) values (?,?,?)",
                reading.getDevice().getDevice_id(), reading.getReading(), reading.getDateTime());
    }

//    public Reading getDeviceLatestReading(Device device){
//        return (Reading) jdbcTemplate.query("SELECT reading_id, device_id, reading, date_time FROM [dbo].[readings] " +
//                        "where device_id = ? order by date_time DESC",
//                BeanPropertyRowMapper.newInstance(Reading.class));
//    }
    public void saveReadingIfNotSavedAlready(Reading reading){
        saveReading(reading);
//        if (!this.readingAlreadyExists(reading)){
//            saveReading(reading);
//        }
    }
}
