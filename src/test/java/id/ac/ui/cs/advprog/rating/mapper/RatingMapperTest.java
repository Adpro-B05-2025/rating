package id.ac.ui.cs.advprog.rating.mapper;

import id.ac.ui.cs.advprog.rating.dto.RatingRequest;
import id.ac.ui.cs.advprog.rating.dto.RatingResponse;
import id.ac.ui.cs.advprog.rating.model.Rating;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RatingMapperTest {

    @Test
    void testToEntity() {
        RatingRequest request = RatingRequest.builder()
                .consultationId(UUID.randomUUID())
                .doctorId(UUID.randomUUID())
                .score(4)
                .comment("Very good")
                .build();

        Rating rating = RatingMapper.toEntity(request);

        assertThat(rating.getConsultationId()).isEqualTo(request.getConsultationId());
        assertThat(rating.getDoctorId()).isEqualTo(request.getDoctorId());
        assertThat(rating.getScore()).isEqualTo(request.getScore());
        assertThat(rating.getComment()).isEqualTo(request.getComment());
    }

    @Test
    void testToResponse() {
        Rating rating = Rating.builder()
                .id(UUID.randomUUID())
                .consultationId(UUID.randomUUID())
                .doctorId(UUID.randomUUID())
                .score(5)
                .comment("Excellent")
                .createdAt(LocalDateTime.now())
                .build();

        RatingResponse response = RatingMapper.toResponse(rating);

        assertThat(response.getId()).isEqualTo(rating.getId());
        assertThat(response.getConsultationId()).isEqualTo(rating.getConsultationId());
        assertThat(response.getDoctorId()).isEqualTo(rating.getDoctorId());
        assertThat(response.getScore()).isEqualTo(rating.getScore());
        assertThat(response.getComment()).isEqualTo(rating.getComment());
        assertThat(response.getCreatedAt()).isEqualTo(rating.getCreatedAt());
    }
}
