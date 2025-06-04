package id.ac.ui.cs.advprog.rating.observer;

import id.ac.ui.cs.advprog.rating.model.Rating;

public interface RatingObserver {
    void onRatingCreated(Rating rating);
    void onRatingUpdated(Rating rating);
    void onRatingDeleted(Long ratingId, Long doctorId);
}