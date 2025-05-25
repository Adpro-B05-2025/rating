package id.ac.ui.cs.advprog.rating.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.rating.dto.RatingRequest;
import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.service.RatingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @TestConfiguration
    static class MockedServiceConfig {
        @Bean
        public RatingService ratingService() {
            return Mockito.mock(RatingService.class);
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

        // Mock Security Context with authenticated user and principal as String userId
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn("1"); // userId as String

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCreateRating_Success() throws Exception {
        when(ratingService.create(any(Rating.class))).thenReturn(rating);

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data[0].score").value(5))
                .andExpect(jsonPath("$.data[0].comment").value("Great consultation!"));
    }

    @Test
    void testCreateRating_BadRequest_WhenConsultationIdNull() throws Exception {
        RatingRequest badRequest = RatingRequest.builder()
                .doctorId(2L)
                .score(5)
                .comment("No consultationId")
                .build();

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateRating_Unauthorized_WhenAuthNull() throws Exception {
        SecurityContextHolder.clearContext(); // auth = null

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> {
                    Assertions.assertInstanceOf(ResponseStatusException.class, result.getResolvedException());
                    Assertions.assertTrue(result.getResolvedException().getMessage().contains("Unauthorized"));
                });
    }

    @Test
    void testCreateRating_Unauthorized_WhenNotAuthenticated() throws Exception {
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(false);
        when(auth.getPrincipal()).thenReturn("1");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> {
                    Assertions.assertInstanceOf(ResponseStatusException.class, result.getResolvedException());
                    Assertions.assertTrue(result.getResolvedException().getMessage().contains("Unauthorized"));
                });
    }

    @Test
    void testCreateRating_Unauthorized_WhenPrincipalIsAnonymousUser() throws Exception {
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn("anonymousUser");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> {
                    Assertions.assertInstanceOf(ResponseStatusException.class, result.getResolvedException());
                    Assertions.assertTrue(result.getResolvedException().getMessage().contains("Unauthorized"));
                });
    }

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
                .andExpect(jsonPath("$.data[0].id").value(rating.getId().intValue()));
    }

    @Test
    void testGetRatingsByDoctorId() throws Exception {
        when(ratingService.findAllByDoctorId(rating.getDoctorId()))
                .thenReturn(Collections.singletonList(rating));

        mockMvc.perform(get("/api/rating/doctor/" + rating.getDoctorId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.data[0].doctorId").value(rating.getDoctorId().intValue()));
    }

    @Test
    void testGetRatingsByConsultationId() throws Exception {
        when(ratingService.findAllByConsultationId(rating.getConsultationId()))
                .thenReturn(Collections.singletonList(rating));

        mockMvc.perform(get("/api/rating/consultation/" + rating.getConsultationId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.data[0].consultationId").value(rating.getConsultationId().intValue()));
    }

    @Test
    void testUpdateRating() throws Exception {
        when(ratingService.update(any(Long.class), any(Rating.class))).thenReturn(rating);

        mockMvc.perform(put("/api/rating/" + rating.getId())
                        .contentType(MediaType.APPLICATION_JSON)
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
