package com.example.demo.Device;
import com.example.demo.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="userDevices")
public class UserDevices{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, unique = false)
    public User user;

    @ManyToOne
    @JoinColumn(name="device_id", nullable=false, unique = false)
    public Device device;
}
