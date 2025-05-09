package id.ac.ui.cs.advprog.rating.strategy;

import id.ac.ui.cs.advprog.rating.model.Rating;
import org.springframework.stereotype.Component;

@Component
public class DefaultRatingValidation implements RatingValidationStrategy {

    @Override
    public void validate(Rating rating) {
        if (rating.getScore() == null || rating.getScore() < 1 || rating.getScore() > 5) {
            throw new IllegalArgumentException("Rating score must be between 1 and 5");
        }
        if (rating.getDoctorId() == null || rating.getUserId() == null || rating.getConsultationId() == null) {
            throw new IllegalArgumentException("Doctor ID, User ID, and Consultation ID must be provided");
        }
    }
}
