package com.example.demo.Reading;
import com.example.demo.Device.Device;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="readings")
public class Reading {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Integer reading_id;

    @ManyToOne
    @JoinColumn(name="device_id", nullable=false)
    @JsonIgnore
    private Device device;

    @Column(nullable=false)
    private String reading;

    @Column(columnDefinition="DATETIME", nullable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
}
