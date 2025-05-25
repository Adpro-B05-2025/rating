package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RatingServiceImplTest {

    private RatingRepository ratingRepository;
    private RestTemplate restTemplate;
    private RatingServiceImpl ratingService;
    private Rating rating;

    @BeforeEach
    void setUp() {
        ratingRepository = mock(RatingRepository.class);
        restTemplate = mock(RestTemplate.class);
        ratingService = new RatingServiceImpl(ratingRepository);
        try {
            var field = RatingServiceImpl.class.getDeclaredField("restTemplate");
            field.setAccessible(true);
            field.set(ratingService, restTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        rating = Rating.builder()
                .id(4L)
                .consultationId(2L)
                .doctorId(2L)
                .score(4)
                .comment("Dokternya informatif")
                .build();
        rating.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testCreate_SuccessfulUpdateAverageRatingCall() {
        when(ratingRepository.save(any(Rating.class))).thenAnswer(i -> i.getArgument(0));
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.PATCH),
                any(HttpEntity.class),
                eq(String.class)))
                .thenReturn(ResponseEntity.ok("Success"));

        Rating saved = ratingService.create(rating);

        assertThat(saved.getScore()).isEqualTo(4);
        assertThat(saved.getCreatedAt()).isNotNull();
        verify(ratingRepository, times(1)).save(saved);

        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpEntity> entityCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        verify(restTemplate, times(1)).exchange(
                urlCaptor.capture(),
                eq(HttpMethod.PATCH),
                entityCaptor.capture(),
                eq(String.class));

        assertThat(urlCaptor.getValue()).contains(saved.getDoctorId().toString());

        HttpEntity<?> capturedEntity = entityCaptor.getValue();
        HttpHeaders headers = (HttpHeaders) capturedEntity.getHeaders();
        assertThat(headers.getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void testCreate_FailedUpdateAverageRatingCall() {
        when(ratingRepository.save(any(Rating.class))).thenAnswer(i -> i.getArgument(0));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.status(500).body("Error"));

        Rating saved = ratingService.create(rating);

        assertThat(saved).isNotNull();
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.PATCH),
                any(HttpEntity.class),
                eq(String.class));
    }

    @Test
    void testCreate_ExceptionInUpdateAverageRatingCall() {
        when(ratingRepository.save(any(Rating.class))).thenAnswer(i -> i.getArgument(0));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("Connection refused"));

        Rating saved = ratingService.create(rating);

        assertThat(saved).isNotNull();
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.PATCH),
                any(HttpEntity.class),
                eq(String.class));
    }

    @Test
    void testUpdate_Success() {
        Rating updatedRating = Rating.builder()
                .score(5)
                .comment("Sangat bagus")
                .build();

        when(ratingRepository.findById(4L)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenAnswer(i -> i.getArgument(0));

        Rating result = ratingService.update(4L, updatedRating);

        assertThat(result.getScore()).isEqualTo(5);
        assertThat(result.getComment()).isEqualTo("Sangat bagus");
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void testUpdate_NotFound() {
        when(ratingRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ratingService.update(99L, rating));
    }

    @Test
    void testDeleteById_Success() {
        when(ratingRepository.existsById(4L)).thenReturn(true);
        ratingService.deleteById(4L);
        verify(ratingRepository).deleteById(4L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(ratingRepository.existsById(99L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> ratingService.deleteById(99L));
    }

    @Test
    void testFindById_Success() {
        when(ratingRepository.findById(4L)).thenReturn(Optional.of(rating));
        Rating found = ratingService.findById(4L);
        assertThat(found).isEqualTo(rating);
    }

    @Test
    void testFindById_NotFound() {
        when(ratingRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> ratingService.findById(99L));
    }

    @Test
    void testFindAll() {
        when(ratingRepository.findAll()).thenReturn(List.of(rating));
        List<Rating> result = ratingService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(rating);
    }

    @Test
    void testFindAllByDoctorId() {
        when(ratingRepository.findAllByDoctorId(2L)).thenReturn(List.of(rating));
        List<Rating> result = ratingService.findAllByDoctorId(2L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(rating);
    }

    @Test
    void testFindAllByConsultationId() {
        when(ratingRepository.findAllByConsultationId(2L)).thenReturn(List.of(rating));
        List<Rating> result = ratingService.findAllByConsultationId(2L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(rating);
    }
}
