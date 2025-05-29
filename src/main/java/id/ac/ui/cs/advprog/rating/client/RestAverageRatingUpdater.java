package id.ac.ui.cs.advprog.rating.client;

import id.ac.ui.cs.advprog.rating.service.AverageRatingUpdater;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestAverageRatingUpdater implements AverageRatingUpdater {

    private final RestTemplate restTemplate;
    private final String authProfileBaseUrl = "http://localhost:8081/api/caregiver";

    public RestAverageRatingUpdater(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    @Override
    public void updateAverageRating(Long doctorId) {
        String url = authProfileBaseUrl + "/" + doctorId + "/averageRating";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
    }
}
