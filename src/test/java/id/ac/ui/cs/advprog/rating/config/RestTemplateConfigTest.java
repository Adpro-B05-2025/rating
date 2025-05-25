package id.ac.ui.cs.advprog.rating.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class RestTemplateConfigTest {

    @Test
    void restTemplateBeanShouldNotBeNull() {
        RestTemplateConfig config = new RestTemplateConfig();
        RestTemplate restTemplate = config.restTemplate();

        assertNotNull(restTemplate);
    }
}
