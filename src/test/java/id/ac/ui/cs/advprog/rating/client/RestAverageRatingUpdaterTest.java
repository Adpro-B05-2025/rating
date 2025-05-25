package id.ac.ui.cs.advprog.rating.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

class RestAverageRatingUpdaterTest {

    private RestTemplate restTemplate;
    private RestAverageRatingUpdater updater;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        updater = new RestAverageRatingUpdater(restTemplate);
    }

    @Test
    void testUpdateAverageRatingShouldCallRestTemplate() {
        Long doctorId = 99L;
        String expectedUrl = "http://localhost:8081/api/caregiver/99/averageRating";

        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(HttpMethod.PATCH),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        updater.updateAverageRating(doctorId);

        verify(restTemplate, times(1)).exchange(
                eq(expectedUrl),
                eq(HttpMethod.PATCH),
                any(HttpEntity.class),
                eq(String.class)
        );
    }
}
