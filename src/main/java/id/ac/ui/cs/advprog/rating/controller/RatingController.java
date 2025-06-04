package id.ac.ui.cs.advprog.rating.controller;

import id.ac.ui.cs.advprog.rating.dto.ApiResponse;
import id.ac.ui.cs.advprog.rating.dto.RatingRequest;
import id.ac.ui.cs.advprog.rating.dto.RatingResponse;
import id.ac.ui.cs.advprog.rating.mapper.RatingMapper;
import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000", "https://lucent-treacle-8937a6.netlify.app", "https://www.pandacare-rating.com"}, allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    private Long getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: JWT token missing or invalid");
        }
        return Long.valueOf((String) auth.getPrincipal());
    }

    @PostMapping
    public ApiResponse<RatingResponse> createRating(@RequestBody RatingRequest request) {
        if (request.getConsultationId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consultation ID must be provided");
        }

        Long userId = getAuthenticatedUserId();

        Rating rating = RatingMapper.toEntity(request, userId);
        rating.setUserId(userId);

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
    public ApiResponse<RatingResponse> getRatingById(@PathVariable Long id) {
        Rating rating = ratingService.findById(id);
        RatingResponse response = RatingMapper.toResponse(rating);

        return ApiResponse.<RatingResponse>builder()
                .success(1)
                .message("OK")
                .data(Collections.singletonList(response))
                .build();
    }

    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<RatingResponse> getRatingsByDoctorId(@PathVariable Long doctorId) {
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
    public ApiResponse<RatingResponse> getRatingsByConsultationId(@PathVariable Long consultationId) {
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
    public ApiResponse<RatingResponse> updateRating(@PathVariable Long id, @RequestBody RatingRequest request) {
        Long userId = getAuthenticatedUserId();

        Rating updatedRating = RatingMapper.toEntity(request, userId);
        Rating saved = ratingService.update(id, updatedRating);
        RatingResponse response = RatingMapper.toResponse(saved);

        return ApiResponse.<RatingResponse>builder()
                .success(1)
                .message("OK")
                .data(Collections.singletonList(response))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRating(@PathVariable Long id) {
        ratingService.deleteById(id);
        return ApiResponse.<Void>builder()
                .success(1)
                .message("Deleted successfully")
                .data(null)
                .build();
    }
}
