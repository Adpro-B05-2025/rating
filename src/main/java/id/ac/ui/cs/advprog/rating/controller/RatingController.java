package id.ac.ui.cs.advprog.rating.controller;

import id.ac.ui.cs.advprog.rating.dto.RatingRequest;
import id.ac.ui.cs.advprog.rating.dto.RatingResponse;
import id.ac.ui.cs.advprog.rating.mapper.RatingMapper;
import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public RatingResponse createRating(@RequestBody RatingRequest request) {
        Rating rating = RatingMapper.toEntity(request);
        Rating created = ratingService.create(rating);
        return RatingMapper.toResponse(created);
    }

    @GetMapping
    public List<RatingResponse> getAllRatings() {
        return ratingService.findAll()
                .stream()
                .map(RatingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RatingResponse getRatingById(@PathVariable UUID id) {
        Rating rating = ratingService.findById(id);
        return RatingMapper.toResponse(rating);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<RatingResponse> getRatingsByDoctorId(@PathVariable UUID doctorId) {
        return ratingService.findAllByDoctorId(doctorId)
                .stream()
                .map(RatingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<RatingResponse> getRatingsByUserId(@PathVariable UUID userId) {
        return ratingService.findAllByUserId(userId)
                .stream()
                .map(RatingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/consultation/{consultationId}")
    public List<RatingResponse> getRatingsByConsultationId(@PathVariable UUID consultationId) {
        return ratingService.findAllByConsultationId(consultationId)
                .stream()
                .map(RatingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public RatingResponse updateRating(@PathVariable UUID id, @RequestBody RatingRequest request) {
        Rating updatedRating = RatingMapper.toEntity(request);
        Rating saved = ratingService.update(id, updatedRating);
        return RatingMapper.toResponse(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable UUID id) {
        ratingService.deleteById(id);
    }
}
