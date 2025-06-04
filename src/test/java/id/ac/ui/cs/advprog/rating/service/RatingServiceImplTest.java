package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.observer.RatingObserver;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RatingServiceImplTest {

    private RatingRepository ratingRepository;
    private RatingServiceImpl ratingService;
    private Rating rating;
    private RatingObserver ratingObserver;

    @BeforeEach
    void setUp() {
        ratingRepository = mock(RatingRepository.class);
        ratingService = new RatingServiceImpl(ratingRepository);

        ratingObserver = mock(RatingObserver.class);
        ratingService.addObserver(ratingObserver);

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
    void testCreate_ShouldNotifyObserver() {
        when(ratingRepository.save(any(Rating.class))).thenAnswer(i -> i.getArgument(0));

        Rating saved = ratingService.create(rating);

        assertThat(saved.getScore()).isEqualTo(4);
        assertThat(saved.getCreatedAt()).isNotNull();

        verify(ratingRepository, times(1)).save(saved);
        verify(ratingObserver, times(1)).onRatingCreated(saved);
    }

    @Test
    void testUpdate_ShouldNotifyObserver() {
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
        verify(ratingObserver, times(1)).onRatingUpdated(result);
    }

    @Test
    void testUpdate_NotFound() {
        when(ratingRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ratingService.update(99L, rating));
        verifyNoInteractions(ratingObserver);
    }

    @Test
    void testDeleteById_ShouldNotifyObserver() {
        when(ratingRepository.existsById(4L)).thenReturn(true);
        when(ratingRepository.findById(4L)).thenReturn(Optional.of(rating));

        ratingService.deleteById(4L);

        verify(ratingRepository).deleteById(4L);
        verify(ratingObserver, times(1)).onRatingDeleted(4L, rating.getDoctorId());
    }

    @Test
    void testDeleteById_NotFound() {
        when(ratingRepository.existsById(99L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> ratingService.deleteById(99L));
        verifyNoInteractions(ratingObserver);
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
        assertThat(result.getFirst()).isEqualTo(rating);
    }

    @Test
    void testFindAllByDoctorId() {
        when(ratingRepository.findAllByDoctorId(2L)).thenReturn(List.of(rating));
        List<Rating> result = ratingService.findAllByDoctorId(2L);
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(rating);
    }

    @Test
    void testFindAllByConsultationId() {
        when(ratingRepository.findAllByConsultationId(2L)).thenReturn(List.of(rating));
        List<Rating> result = ratingService.findAllByConsultationId(2L);
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(rating);
    }

    @Test
    void testCalculateDoctorAverageRating_WithRatings() {
        Rating rating1 = Rating.builder().score(4).build();
        Rating rating2 = Rating.builder().score(5).build();
        Rating rating3 = Rating.builder().score(3).build();

        when(ratingRepository.findAllByDoctorId(2L))
                .thenReturn(List.of(rating1, rating2, rating3));

        double avg = ratingService.calculateDoctorAverageRating(2L);

        assertThat(avg).isEqualTo((4 + 5 + 3) / 3.0);
    }

    @Test
    void testCalculateDoctorAverageRating_NoRatings() {
        when(ratingRepository.findAllByDoctorId(2L)).thenReturn(List.of());

        double avg = ratingService.calculateDoctorAverageRating(2L);

        assertThat(avg).isEqualTo(0.0);
    }

    @Test
    void testRemoveObserver_ShouldNoLongerReceiveNotifications() {
        ratingService.removeObserver(ratingObserver);

        when(ratingRepository.save(any(Rating.class))).thenAnswer(i -> i.getArgument(0));
        Rating newRating = Rating.builder()
                .id(5L)
                .consultationId(3L)
                .doctorId(2L)
                .score(5)
                .comment("Hebat")
                .build();

        ratingService.create(newRating);

        verify(ratingObserver, never()).onRatingCreated(any());
    }

}
