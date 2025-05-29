package id.ac.ui.cs.advprog.rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RatingApplication {

    public static void main(String[] args) {
      SpringApplication.run(RatingApplication.class, args);
    }

}
