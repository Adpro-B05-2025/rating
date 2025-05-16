package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;

import java.util.List;
import java.util.UUID;

public interface RatingService {
    Rating create(Rating rating);
    Rating update(UUID id, Rating updatedRating);
    List<Rating> findAll();
    void deleteById(UUID id);
    Rating findById(UUID id);
    List<Rating> findAllByDoctorId(Long doctorId);
    List<Rating> findAllByConsultationId(UUID consultationId);
}