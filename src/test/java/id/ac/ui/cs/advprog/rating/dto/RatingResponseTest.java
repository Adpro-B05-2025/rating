package id.ac.ui.cs.advprog.rating.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RatingResponseTest {

    @Test
    void testBuilderAndGetter() {

        LocalDateTime createdAt = LocalDateTime.now();

        RatingResponse response = RatingResponse.builder()
                .id(1L)
                .consultationId(2L)
                .doctorId(2L)
                .score(4)
                .comment("Baik")
                .createdAt(createdAt)
                .build();

        assertEquals(1L, response.getId());
        assertEquals(2L, response.getConsultationId());
        assertEquals(2L, response.getDoctorId());
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
