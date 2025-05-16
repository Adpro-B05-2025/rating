package id.ac.ui.cs.advprog.rating.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    @Test
    void testCreateRatingConstructor() {
        UUID consultationId = UUID.randomUUID();

        Rating rating = new Rating(consultationId, 2L, 1L, 5, "Great consultation!");

        assertEquals(consultationId, rating.getConsultationId());
        assertEquals(2L, rating.getDoctorId());
        assertEquals(5, rating.getScore());
        assertEquals("Great consultation!", rating.getComment());
        assertNotNull(rating.getCreatedAt());
    }
}
