package id.ac.ui.cs.advprog.rating.repository;

import id.ac.ui.cs.advprog.rating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, UUID> {
    List<Rating> findAllByDoctorId(UUID doctorId);
    List<Rating> findAllByUserId(UUID userId);
    List<Rating> findAllByConsultationId(UUID consultationId);
}
