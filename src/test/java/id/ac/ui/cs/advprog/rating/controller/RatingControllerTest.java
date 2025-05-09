package id.ac.ui.cs.advprog.rating.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.rating.dto.RatingRequest;
import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
@Import(RatingControllerTest.MockedServiceConfig.class)
public class RatingControllerTest {

    @TestConfiguration
    static class MockedServiceConfig {
        @Bean
        public RatingService ratingService() {
            return org.mockito.Mockito.mock(RatingService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rating rating;
    private RatingRequest request;

    @BeforeEach
    void setUp() {
        rating = Rating.builder()
                .id(UUID.randomUUID())
                .consultationId(UUID.randomUUID())
                .doctorId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .score(5)
                .comment("Great consultation!")
                .createdAt(LocalDateTime.now())
                .build();

        request = RatingRequest.builder()
                .consultationId(rating.getConsultationId())
                .doctorId(rating.getDoctorId())
                .userId(rating.getUserId())
                .score(rating.getScore())
                .comment(rating.getComment())
                .build();
    }

    @Test
    void testCreateRating() throws Exception {
        when(ratingService.create(any(Rating.class))).thenReturn(rating);

        mockMvc.perform(post("/api/rating")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(5))
                .andExpect(jsonPath("$.comment").value("Great consultation!"));
    }

    @Test
    void testGetAllRatings() throws Exception {
        when(ratingService.findAll()).thenReturn(Collections.singletonList(rating));

        mockMvc.perform(get("/api/rating"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("Great consultation!"));
    }

    @Test
    void testGetRatingById() throws Exception {
        when(ratingService.findById(rating.getId())).thenReturn(rating);

        mockMvc.perform(get("/api/rating/" + rating.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rating.getId().toString()));
    }
}
