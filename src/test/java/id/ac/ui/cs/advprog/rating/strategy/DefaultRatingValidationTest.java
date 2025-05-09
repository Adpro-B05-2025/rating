package id.ac.ui.cs.advprog.rating.strategy;

import id.ac.ui.cs.advprog.rating.model.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultRatingValidationTest {

    private DefaultRatingValidation defaultRatingValidation;

    @BeforeEach
    void setUp() {
        defaultRatingValidation = new DefaultRatingValidation();
    }

    @Test
    void testValidRating() {
        Rating validRating = new Rating();
        validRating.setScore(4);
        validRating.setDoctorId(UUID.randomUUID());
        validRating.setUserId(UUID.randomUUID());
        validRating.setConsultationId(UUID.randomUUID());

        assertDoesNotThrow(() -> defaultRatingValidation.validate(validRating));
    }

    @Test
    void testRatingWithInvalidScore() {
        Rating invalidRating = new Rating();
        invalidRating.setScore(6);
        invalidRating.setDoctorId(UUID.randomUUID());
        invalidRating.setUserId(UUID.randomUUID());
        invalidRating.setConsultationId(UUID.randomUUID());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> defaultRatingValidation.validate(invalidRating));
        assertEquals("Rating score must be between 1 and 5", exception.getMessage());
    }

    @Test
    void testRatingWithNullDoctorId() {
        Rating invalidRating = new Rating();
        invalidRating.setScore(4);
        invalidRating.setDoctorId(null);
        invalidRating.setUserId(UUID.randomUUID());
        invalidRating.setConsultationId(UUID.randomUUID());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> defaultRatingValidation.validate(invalidRating));
        assertEquals("Doctor ID, User ID, and Consultation ID must be provided", exception.getMessage());
    }

    @Test
    void testRatingWithNullUserId() {
        Rating invalidRating = new Rating();
        invalidRating.setScore(4);
        invalidRating.setDoctorId(UUID.randomUUID());
        invalidRating.setUserId(null);
        invalidRating.setConsultationId(UUID.randomUUID());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> defaultRatingValidation.validate(invalidRating));
        assertEquals("Doctor ID, User ID, and Consultation ID must be provided", exception.getMessage());
    }

    @Test
    void testRatingWithNullConsultationId() {
        Rating invalidRating = new Rating();
        invalidRating.setScore(4);
        invalidRating.setDoctorId(UUID.randomUUID());
        invalidRating.setUserId(UUID.randomUUID());
        invalidRating.setConsultationId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> defaultRatingValidation.validate(invalidRating));
        assertEquals("Doctor ID, User ID, and Consultation ID must be provided", exception.getMessage());
    }
}