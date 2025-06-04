package id.ac.ui.cs.advprog.rating.observer;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.service.AverageRatingUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AverageRatingObserverTest {

    private AverageRatingUpdater averageRatingUpdater;
    private AverageRatingObserver averageRatingObserver;

    @BeforeEach
    void setUp() {
        averageRatingUpdater = mock(AverageRatingUpdater.class);
        averageRatingObserver = new AverageRatingObserver(averageRatingUpdater);
    }

    @Test
    void testOnRatingCreated_ShouldCallUpdateAverageRating() {
        Rating rating = Rating.builder()
                .doctorId(10L)
                .build();

        averageRatingObserver.onRatingCreated(rating);

        verify(averageRatingUpdater, times(1)).updateAverageRating(10L);
    }

    @Test
    void testOnRatingUpdated_ShouldCallUpdateAverageRating() {
        Rating rating = Rating.builder()
                .doctorId(20L)
                .build();

        averageRatingObserver.onRatingUpdated(rating);

        verify(averageRatingUpdater, times(1)).updateAverageRating(20L);
    }

    @Test
    void testOnRatingDeleted_ShouldCallUpdateAverageRating() {
        Long ratingId = 5L;
        Long doctorId = 30L;

        averageRatingObserver.onRatingDeleted(ratingId, doctorId);

        verify(averageRatingUpdater, times(1)).updateAverageRating(30L);
    }
}
