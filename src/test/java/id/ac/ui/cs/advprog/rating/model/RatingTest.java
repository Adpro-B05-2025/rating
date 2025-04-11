package id.ac.ui.cs.advprog.rating.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    @Test
    void testCreateRatingConstructor() {
        UUID consultationId = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Rating rating = new Rating(consultationId, doctorId, userId, 5, "Great consultation!");

        assertEquals(consultationId, rating.getConsultationId());
        assertEquals(doctorId, rating.getDoctorId());
        assertEquals(userId, rating.getUserId());
        assertEquals(5, rating.getScore());
        assertEquals("Great consultation!", rating.getComment());
        assertNotNull(rating.getCreatedAt());
    }
}
