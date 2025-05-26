package id.ac.ui.cs.advprog.rating.mapper;

import id.ac.ui.cs.advprog.rating.dto.RatingRequest;
import id.ac.ui.cs.advprog.rating.dto.RatingResponse;
import id.ac.ui.cs.advprog.rating.model.Rating;

import java.time.LocalDateTime;

public class RatingMapper {

    public static Rating toEntity(RatingRequest request, Long userId) {
        return Rating.builder()
                .consultationId(request.getConsultationId())
                .doctorId(request.getDoctorId())
                .userId(userId)
                .score(request.getScore())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static RatingResponse toResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .consultationId(rating.getConsultationId())
                .doctorId(rating.getDoctorId())
                .userId(rating.getUserId())
                .score(rating.getScore())
                .comment(rating.getComment())
                .createdAt(rating.getCreatedAt())
                .build();
    }
}
