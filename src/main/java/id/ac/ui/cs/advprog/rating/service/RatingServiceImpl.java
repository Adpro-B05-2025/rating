package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return ratingRepository.save(rating);
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

    @Override
    public void deleteById(UUID id) {
        ratingRepository.deleteById(id);
    }
}
