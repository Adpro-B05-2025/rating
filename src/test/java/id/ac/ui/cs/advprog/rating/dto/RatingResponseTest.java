package id.ac.ui.cs.advprog.rating.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RatingResponseTest {

    @Test
    void testBuilderAndGetter() {
        UUID id = UUID.randomUUID();
        UUID consultationId = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        RatingResponse response = RatingResponse.builder()
                .id(id)
                .consultationId(consultationId)
                .doctorId(doctorId)
                .score(4)
                .comment("Baik")
                .createdAt(createdAt)
                .build();

        assertEquals(id, response.getId());
        assertEquals(consultationId, response.getConsultationId());
        assertEquals(doctorId, response.getDoctorId());
        assertEquals(4, response.getScore());
        assertEquals("Baik", response.getComment());
        assertEquals(createdAt, response.getCreatedAt());
    }

    @Test
    void testSetter() {
        RatingResponse response = new RatingResponse();
        response.setScore(2);
        response.setComment("Kurang baik");

        assertEquals(2, response.getScore());
        assertEquals("Kurang baik", response.getComment());
    }
}
