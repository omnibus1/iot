package com.example.demo;

import com.azure.messaging.eventgrid.EventGridEvent;
import com.example.demo.EventHandler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.function.Consumer;

@SpringBootApplication
public class Demo1Application {

    @Autowired
    EventHandler eventHandler;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandler.class);

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

    @Bean
    public Consumer<Message<String>> consume() {
        return message -> {
            List<EventGridEvent> eventData = EventGridEvent.fromString(message.getPayload());
            eventData.forEach(event -> {
                eventHandler.handleEvent(event);
            });
        };
    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
