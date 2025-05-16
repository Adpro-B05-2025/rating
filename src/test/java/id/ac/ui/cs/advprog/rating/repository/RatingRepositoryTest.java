package id.ac.ui.cs.advprog.rating.repository;

import id.ac.ui.cs.advprog.rating.model.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    private Rating rating;

    @BeforeEach
    void setUp() {
        rating = new Rating(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                5,
                "Mantap dokternya!"
        );
        ratingRepository.save(rating);
    }

    @Test
    void testFindByDoctorId() {
        List<Rating> result = ratingRepository.findAllByDoctorId(rating.getDoctorId());
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getScore()).isEqualTo(5);
    }

    @Test
    void testFindByConsultationId() {
        List<Rating> result = ratingRepository.findAllByConsultationId(rating.getConsultationId());
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getConsultationId()).isEqualTo(rating.getConsultationId());
    }

    @Test
    void testSaveAndRetrieve() {
        var saved = ratingRepository.findById(rating.getId());
        assertThat(saved).isPresent();
        assertThat(saved.get().getScore()).isEqualTo(5);
    }
}
