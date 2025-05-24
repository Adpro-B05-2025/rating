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
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(Long id, Rating updatedRating) {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));

        existingRating.setScore(updatedRating.getScore());
        existingRating.setComment(updatedRating.getComment());
        return ratingRepository.save(existingRating);
    }

    @Override
    public void deleteById(Long id) {
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


