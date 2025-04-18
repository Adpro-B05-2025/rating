package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;

import java.util.List;
import java.util.UUID;

public interface RatingService {
    Rating create(Rating rating);
    List<Rating> findAll();
    Rating findById(UUID id);
    List<Rating> findAllByDoctorId(UUID doctorId);
    List<Rating> findAllByUserId(UUID userId);
    List<Rating> findAllByConsultationId(UUID consultationId);
    void deleteById(UUID id);
}
