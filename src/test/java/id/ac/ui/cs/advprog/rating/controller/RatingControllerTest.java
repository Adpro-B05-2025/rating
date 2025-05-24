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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(RatingController.class)
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
                .id(1L)
                .consultationId(2L)
                .doctorId(2L)
                .score(5)
                .comment("Great consultation!")
                .createdAt(LocalDateTime.now())
                .build();

        request = RatingRequest.builder()
                .consultationId(rating.getConsultationId())
                .doctorId(rating.getDoctorId())
                .score(rating.getScore())
                .comment(rating.getComment())
                .build();
    }

//    @Test
//    void testCreateRating() throws Exception {
//        when(ratingService.create(any(Rating.class))).thenReturn(rating);
//
//        mockMvc.perform(post("/api/rating")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(1))
//                .andExpect(jsonPath("$.message").value("OK"))
//                .andExpect(jsonPath("$.data[0].score").value(5))
//                .andExpect(jsonPath("$.data[0].comment").value("Great consultation!"));
//    }

    @Test
    void testGetAllRatings() throws Exception {
        when(ratingService.findAll()).thenReturn(Collections.singletonList(rating));

        mockMvc.perform(get("/api/rating"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data[0].comment").value("Great consultation!"));
    }

    @Test
    void testGetRatingById() throws Exception {
        when(ratingService.findById(rating.getId())).thenReturn(rating);

        mockMvc.perform(get("/api/rating/" + rating.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data[0].id").value(rating.getId().toString()));
    }

    @Test
    void testGetRatingsByDoctorId() throws Exception {
        when(ratingService.findAllByDoctorId(rating.getDoctorId()))
                .thenReturn(Collections.singletonList(rating));

        mockMvc.perform(get("/api/rating/doctor/" + rating.getDoctorId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.data[0].doctorId").value(rating.getDoctorId()));
    }

    @Test
    void testGetRatingsByConsultationId() throws Exception {
        when(ratingService.findAllByConsultationId(rating.getConsultationId()))
                .thenReturn(Collections.singletonList(rating));

        mockMvc.perform(get("/api/rating/consultation/" + rating.getConsultationId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.data[0].consultationId").value(rating.getConsultationId().toString()));
    }

    @Test
    void testUpdateRating() throws Exception {
        when(ratingService.update(any(Long.class), any(Rating.class))).thenReturn(rating);

        mockMvc.perform(put("/api/rating/" + rating.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data[0].score").value(5));
    }

    @Test
    void testDeleteRating() throws Exception {
        mockMvc.perform(delete("/api/rating/" + rating.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.message").value("Deleted successfully"));
    }
}
