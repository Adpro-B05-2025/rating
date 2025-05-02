package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating create(Rating rating) {
        validateRating(rating);
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(UUID id, Rating updatedRating) {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));

        validateRating(updatedRating);
        existingRating.setScore(updatedRating.getScore());
        existingRating.setComment(updatedRating.getComment());

        return ratingRepository.save(existingRating);
    }

    @Override
    public void deleteById(UUID id) {
        if (!ratingRepository.existsById(id)) {
            throw new IllegalArgumentException("Rating not found with id: " + id);
        }
        ratingRepository.deleteById(id);
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(UUID id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
    }

    @Override
    public List<Rating> findAllByDoctorId(UUID doctorId) {
        return ratingRepository.findAllByDoctorId(doctorId);
    }

    @Override
    public List<Rating> findAllByUserId(UUID userId) {
        return ratingRepository.findAllByUserId(userId);
    }

    @Override
    public List<Rating> findAllByConsultationId(UUID consultationId) {
        return ratingRepository.findAllByConsultationId(consultationId);
    }

    private void validateRating(Rating rating) {
        if (rating.getScore() == null || rating.getScore() < 1 || rating.getScore() > 5) {
            throw new IllegalArgumentException("Rating score must be between 1 and 5");
        }
        if (rating.getDoctorId() == null || rating.getUserId() == null || rating.getConsultationId() == null) {
            throw new IllegalArgumentException("Doctor ID, User ID, and Consultation ID must be provided");
        }
    }
}


