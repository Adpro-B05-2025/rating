package id.ac.ui.cs.advprog.rating.controller;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public Rating createRating(@RequestBody Rating rating) {
        return ratingService.create(rating);
    }

    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingService.findAll();
    }

    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable UUID id) {
        return ratingService.findById(id);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Rating> getRatingsByDoctorId(@PathVariable UUID doctorId) {
        return ratingService.findAllByDoctorId(doctorId);
    }

    @GetMapping("/user/{userId}")
    public List<Rating> getRatingsByUserId(@PathVariable UUID userId) {
        return ratingService.findAllByUserId(userId);
    }

    @GetMapping("/consultation/{consultationId}")
    public List<Rating> getRatingsByConsultationId(@PathVariable UUID consultationId) {
        return ratingService.findAllByConsultationId(consultationId);
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable UUID id) {
        ratingService.deleteById(id);
    }
}
