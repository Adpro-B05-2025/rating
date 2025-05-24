package id.ac.ui.cs.advprog.rating.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    @Test
    void testCreateRatingConstructor() {
        UUID consultationId = UUID.randomUUID();

        Rating rating = new Rating(4L, 2L, 1L, 5, "Great consultation!");

        assertEquals(4L, rating.getConsultationId());
        assertEquals(2L, rating.getDoctorId());
        assertEquals(5, rating.getScore());
        assertEquals("Great consultation!", rating.getComment());
        assertNotNull(rating.getCreatedAt());
    }
}
