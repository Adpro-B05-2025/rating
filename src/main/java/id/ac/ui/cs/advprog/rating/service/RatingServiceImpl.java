package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import id.ac.ui.cs.advprog.rating.observer.RatingObserver;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final List<RatingObserver> observers = new ArrayList<>();

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void addObserver(RatingObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(RatingObserver observer) {
        observers.remove(observer);
    }

    private void notifyRatingCreated(Rating rating) {
        observers.forEach(observer -> {
            try {
                observer.onRatingCreated(rating);
            } catch (Exception e) {
                System.err.println("Error notifying observer: " + e.getMessage());
            }
        });
    }

    private void notifyRatingUpdated(Rating rating) {
        observers.forEach(observer -> {
            try {
                observer.onRatingUpdated(rating);
            } catch (Exception e) {
                System.err.println("Error notifying observer: " + e.getMessage());
            }
        });
    }

    private void notifyRatingDeleted(Long ratingId, Long doctorId) {
        observers.forEach(observer -> {
            try {
                observer.onRatingDeleted(ratingId, doctorId);
            } catch (Exception e) {
                System.err.println("Error notifying observer: " + e.getMessage());
            }
        });
    }

    @Override
    public Rating create(Rating rating) {
        rating.setCreatedAt(LocalDateTime.now());
        Rating saved = ratingRepository.save(rating);

        notifyRatingCreated(saved);

        return saved;
    }

    @Override
    public Rating update(Long id, Rating updatedRating) {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));

        existingRating.setScore(updatedRating.getScore());
        existingRating.setComment(updatedRating.getComment());

        Rating saved = ratingRepository.save(existingRating);

        notifyRatingUpdated(saved);

        return saved;
    }

    @Override
    public void deleteById(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new IllegalArgumentException("Rating not found with id: " + id);
        }

        Rating rating = findById(id);
        ratingRepository.deleteById(id);

        notifyRatingDeleted(id, rating.getDoctorId());
    }

    public double calculateDoctorAverageRating(Long doctorId) {
        List<Rating> ratings = findAllByDoctorId(doctorId);
        return ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
    }

    @Override
    public List<Rating> findAllByDoctorId(Long doctorId) {
        return ratingRepository.findAllByDoctorId(doctorId);
    }

    @Override
    public List<Rating> findAllByConsultationId(Long consultationId) {
        return ratingRepository.findAllByConsultationId(consultationId);
    }
}