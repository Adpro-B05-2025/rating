package id.ac.ui.cs.advprog.rating.observer;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.service.AverageRatingUpdater;
import org.springframework.stereotype.Component;

@Component
public class AverageRatingObserver implements RatingObserver {

    private final AverageRatingUpdater averageRatingUpdater;

    public AverageRatingObserver(AverageRatingUpdater averageRatingUpdater) {
        this.averageRatingUpdater = averageRatingUpdater;
    }

    @Override
    public void onRatingCreated(Rating rating) {
        averageRatingUpdater.updateAverageRating(rating.getDoctorId());
    }

    @Override
    public void onRatingUpdated(Rating rating) {
        averageRatingUpdater.updateAverageRating(rating.getDoctorId());
    }

    @Override
    public void onRatingDeleted(Long ratingId, Long doctorId) {
        averageRatingUpdater.updateAverageRating(doctorId);
    }
}