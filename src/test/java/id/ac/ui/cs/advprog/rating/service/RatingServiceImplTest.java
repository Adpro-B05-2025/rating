package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RatingServiceImplTest {

    private RatingRepository ratingRepository;
    private RatingServiceImpl ratingService;
    private Rating rating;

    @BeforeEach
    void setUp() {
        ratingRepository = Mockito.mock(RatingRepository.class);
        ratingService = new RatingServiceImpl(ratingRepository);

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
    void testCreate() {
        when(ratingRepository.save(any(Rating.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Rating saved = ratingService.create(rating);
        assertThat(saved.getScore()).isEqualTo(4);
        assertThat(saved.getCreatedAt()).isNotNull();
        verify(ratingRepository, times(1)).save(saved);
    }

    @Test
    void testUpdateSuccess() {
        Rating updatedRating = new Rating(
                3L,
                rating.getConsultationId(),
                2L,
                rating.getUserId(),
                5,
                "Sangat bagus",
                rating.getCreatedAt()
        );

        when(ratingRepository.findById(3L)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(updatedRating);

        Rating result = ratingService.update(3L, updatedRating);

        assertThat(result.getScore()).isEqualTo(5);
        assertThat(result.getComment()).contains("Sangat bagus");
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void testUpdateRatingNotFound() {
        Rating updated = Rating.builder().score(5).comment("Updated").build();

        when(ratingRepository.findById(15L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ratingService.update(15L, updated));
    }

    @Test
    void testFindByIdSuccess() {
        when(ratingRepository.findById(rating.getId())).thenReturn(Optional.of(rating));
        Rating found = ratingService.findById(rating.getId());
        assertThat(found.getComment()).contains("informatif");
    }

    @Test
    void testFindByIdNotFound() {
        UUID fakeId = UUID.randomUUID();
        when(ratingRepository.findById(15L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> ratingService.findById(15L));
    }

    @Test
    void testFindAll() {
        when(ratingRepository.findAll()).thenReturn(List.of(rating));
        List<Rating> result = ratingService.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void testDeleteByIdSuccess() {
        Long id = 1L;
        when(ratingRepository.existsById(id)).thenReturn(true);
        ratingService.deleteById(id);
        verify(ratingRepository).deleteById(id);
    }


    @Test
    void testDeleteByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(ratingRepository.existsById(1L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> ratingService.deleteById(1L));
    }
}
