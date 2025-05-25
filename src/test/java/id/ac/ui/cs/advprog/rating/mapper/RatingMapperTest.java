package id.ac.ui.cs.advprog.rating.mapper;

import id.ac.ui.cs.advprog.rating.dto.RatingRequest;
import id.ac.ui.cs.advprog.rating.dto.RatingResponse;
import id.ac.ui.cs.advprog.rating.model.Rating;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RatingMapperTest {

    @Test
    void privateConstructorCoverage() throws Exception {
        Constructor<RatingMapper> constructor = RatingMapper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }

    @Test
    void testToEntity() {
        RatingRequest request = RatingRequest.builder()
                .consultationId(2L)
                .doctorId(2L)
                .score(4)
                .comment("Very good")
                .build();

        Rating rating = RatingMapper.toEntity(request);

        assertThat(rating).isNotNull();
        assertThat(rating.getConsultationId()).isEqualTo(request.getConsultationId());
        assertThat(rating.getDoctorId()).isEqualTo(request.getDoctorId());
        assertThat(rating.getScore()).isEqualTo(request.getScore());
        assertThat(rating.getComment()).isEqualTo(request.getComment());
        assertThat(rating.getCreatedAt()).isNotNull();
    }

    @Test
    void testToResponse() {
        LocalDateTime now = LocalDateTime.now();

        Rating rating = Rating.builder()
                .id(2L)
                .consultationId(13L)
                .doctorId(2L)
                .score(5)
                .comment("Excellent")
                .createdAt(now)
                .build();

        RatingResponse response = RatingMapper.toResponse(rating);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(rating.getId());
        assertThat(response.getConsultationId()).isEqualTo(rating.getConsultationId());
        assertThat(response.getDoctorId()).isEqualTo(rating.getDoctorId());
        assertThat(response.getScore()).isEqualTo(rating.getScore());
        assertThat(response.getComment()).isEqualTo(rating.getComment());
        assertThat(response.getCreatedAt()).isEqualTo(now);
    }
}
