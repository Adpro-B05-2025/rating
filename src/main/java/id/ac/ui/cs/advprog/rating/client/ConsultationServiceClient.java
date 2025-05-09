package id.ac.ui.cs.advprog.rating.client;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsultationServiceClient {

    public boolean isConsultationValid(UUID consultationId, UUID userId) {
        // Sementara, anggap semua konsultasi valid
        return true;
    }
}
