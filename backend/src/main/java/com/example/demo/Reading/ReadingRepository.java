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

    public List<Reading> getReadingsFromDevice(Device device){
        return jdbcTemplate.query("select * from readings where device_id = ? order by date_time DESC",
                BeanPropertyRowMapper.newInstance(Reading.class), device.getDevice_id());
    }


    public void saveReading(Reading reading){
        jdbcTemplate.update("INSERT INTO readings(device_id, reading, date_time) values (?,?,?)",
                reading.getDevice().getDevice_id(), reading.getReading(), reading.getDateTime());
    }
}
