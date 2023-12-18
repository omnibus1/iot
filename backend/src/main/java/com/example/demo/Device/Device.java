package com.example.demo.Device;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "devices")
public class Device{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer device_id;

    @Column(nullable = false)
    public String serialNumber;
}
