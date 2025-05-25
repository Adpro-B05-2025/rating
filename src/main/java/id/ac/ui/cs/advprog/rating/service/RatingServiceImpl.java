package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RestTemplate restTemplate;

    private final String authProfileBaseUrl = "http://localhost:8081/api/caregiver/";

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Rating create(Rating rating) {
        rating.setCreatedAt(LocalDateTime.now());
        Rating saved = ratingRepository.save(rating);

        // Setelah berhasil simpan, panggil update average rating
        try {
            String url = authProfileBaseUrl + "/" + saved.getDoctorId() + "/averageRating";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>("", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);

            // Optional: cek response
            if (!response.getStatusCode().is2xxSuccessful()) {
                // Log error, tapi jangan throw exception biar rating tetap sukses
                System.err.println("Failed to update average rating for doctorId " + saved.getDoctorId());
            }

        } catch (Exception e) {
            System.err.println("Error updating average rating: " + e.getMessage());
        }

        return saved;
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


