package id.ac.ui.cs.advprog.rating.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RatingRequestTest {

    @Test
    void testBuilderAndGetter() {
        UUID consultationId = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();

        RatingRequest request = RatingRequest.builder()
                .consultationId(consultationId)
                .doctorId(doctorId)
                .score(5)
                .comment("Excellent service")
                .build();

        assertEquals(consultationId, request.getConsultationId());
        assertEquals(doctorId, request.getDoctorId());
        assertEquals(5, request.getScore());
        assertEquals("Excellent service", request.getComment());
    }

    @Test
    void testSetter() {
        RatingRequest request = new RatingRequest();
        request.setScore(3);
        request.setComment("Average");

        assertEquals(3, request.getScore());
        assertEquals("Average", request.getComment());
    }
}
