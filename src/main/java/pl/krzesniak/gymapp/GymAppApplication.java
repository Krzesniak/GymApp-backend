package pl.krzesniak.gymapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableAsync
public class GymAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymAppApplication.class, args);
    }

}
