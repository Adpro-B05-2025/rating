package id.ac.ui.cs.advprog.rating.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsultationServiceClientTest {

    private ConsultationServiceClient consultationServiceClient;

    @BeforeEach
    void setUp() {
        consultationServiceClient = new ConsultationServiceClient();
    }

    @Test
    void testIsConsultationValidAlwaysReturnsTrue() {
        UUID consultationId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        boolean result = consultationServiceClient.isConsultationValid(consultationId, userId);

        assertThat(result).isTrue();
    }
}
