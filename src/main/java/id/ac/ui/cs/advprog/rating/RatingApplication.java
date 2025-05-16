package id.ac.ui.cs.advprog.rating;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RatingApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("PASSWORD_DB", dotenv.get("PASSWORD_DB"));

        SpringApplication.run(RatingApplication.class, args);
    }

}
