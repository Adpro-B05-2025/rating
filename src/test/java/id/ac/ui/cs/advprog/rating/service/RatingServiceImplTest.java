package id.ac.ui.cs.advprog.rating.service;

import id.ac.ui.cs.advprog.rating.model.Rating;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

        rating = new Rating(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                4,
                "Dokternya informatif"
        );
    }

    @Test
    void testCreate() {
        when(ratingRepository.save(rating)).thenReturn(rating);
        Rating saved = ratingService.create(rating);
        assertThat(saved.getScore()).isEqualTo(4);
        verify(ratingRepository, times(1)).save(rating);
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
        when(ratingRepository.findById(fakeId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> ratingService.findById(fakeId));
    }

    @Test
    void testFindAll() {
        when(ratingRepository.findAll()).thenReturn(List.of(rating));
        List<Rating> result = ratingService.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void testDeleteById() {
        UUID id = rating.getId();
        ratingService.deleteById(id);
        verify(ratingRepository, times(1)).deleteById(id);
    }
}
