package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.observer.RatingObserver;

import java.util.List;

public interface RatingService {
    Rating create(Rating rating);
    Rating update(Long id, Rating updatedRating);
    List<Rating> findAll();
    void deleteById(Long id);
    Rating findById(Long id);
    List<Rating> findAllByDoctorId(Long doctorId);
    List<Rating> findAllByConsultationId(Long consultationId);

    void addObserver(RatingObserver observer);
    void removeObserver(RatingObserver observer);

    double calculateDoctorAverageRating(Long doctorId);
}