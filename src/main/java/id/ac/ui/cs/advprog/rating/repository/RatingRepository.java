package id.ac.ui.cs.advprog.rating.repository;

import id.ac.ui.cs.advprog.rating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByDoctorId(Long doctorId);
    List<Rating> findAllByConsultationId(Long consultationId);
}
