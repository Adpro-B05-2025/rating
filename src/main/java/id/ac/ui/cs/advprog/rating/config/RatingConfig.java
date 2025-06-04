package id.ac.ui.cs.advprog.rating.config;

import id.ac.ui.cs.advprog.rating.observer.RatingObserver;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import id.ac.ui.cs.advprog.rating.service.RatingService;
import id.ac.ui.cs.advprog.rating.service.RatingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class RatingConfig {

    @Bean
    @Primary
    public RatingService ratingService(
            RatingRepository ratingRepository,
            List<RatingObserver> observers) {

        RatingServiceImpl service = new RatingServiceImpl(ratingRepository);

        observers.forEach(service::addObserver);

        return service;
    }
}