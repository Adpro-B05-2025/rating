package id.ac.ui.cs.advprog.rating.controller;

import id.ac.ui.cs.advprog.rating.dto.ApiResponse;
import id.ac.ui.cs.advprog.rating.dto.RatingRequest;
import id.ac.ui.cs.advprog.rating.dto.RatingResponse;
import id.ac.ui.cs.advprog.rating.mapper.RatingMapper;
import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ApiResponse<RatingResponse> createRating(@RequestBody RatingRequest request) {
        Rating rating = RatingMapper.toEntity(request);
        Rating created = ratingService.create(rating);
        RatingResponse response = RatingMapper.toResponse(created);

        return ApiResponse.<RatingResponse>builder()
                .success(1)
                .message("OK")
                .data(Collections.singletonList(response))
                .build();
    }

    @GetMapping
    public ApiResponse<RatingResponse> getAllRatings() {
        List<RatingResponse> ratings = ratingService.findAll()
                .stream()
                .map(RatingMapper::toResponse)
                .collect(Collectors.toList());

        return ApiResponse.<RatingResponse>builder()
                .success(1)
                .message("OK")
                .data(ratings)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RatingResponse> getRatingById(@PathVariable UUID id) {
        Rating rating = ratingService.findById(id);
        RatingResponse response = RatingMapper.toResponse(rating);

        return ApiResponse.<RatingResponse>builder()
                .success(1)
                .message("OK")
                .data(Collections.singletonList(response))
                .build();
    }

    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<RatingResponse> getRatingsByDoctorId(@PathVariable UUID doctorId) {
        List<RatingResponse> ratings = ratingService.findAllByDoctorId(doctorId)
                .stream()
                .map(RatingMapper::toResponse)
                .collect(Collectors.toList());

        return ApiResponse.<RatingResponse>builder()
                .success(1)
                .message("OK")
                .data(ratings)
                .build();
    }

    @GetMapping("/consultation/{consultationId}")
    public ApiResponse<RatingResponse> getRatingsByConsultationId(@PathVariable UUID consultationId) {
        List<RatingResponse> ratings = ratingService.findAllByConsultationId(consultationId)
                .stream()
                .map(RatingMapper::toResponse)
                .collect(Collectors.toList());

        return ApiResponse.<RatingResponse>builder()
                .success(1)
                .message("OK")
                .data(ratings)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RatingResponse> updateRating(@PathVariable UUID id, @RequestBody RatingRequest request) {
        Rating updatedRating = RatingMapper.toEntity(request);
        Rating saved = ratingService.update(id, updatedRating);
        RatingResponse response = RatingMapper.toResponse(saved);

        return ApiResponse.<RatingResponse>builder()
                .success(1)
                .message("OK")
                .data(Collections.singletonList(response))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRating(@PathVariable UUID id) {
        ratingService.deleteById(id);
        return ApiResponse.<Void>builder()
                .success(1)
                .message("Deleted successfully")
                .data(null)
                .build();
    }
}
