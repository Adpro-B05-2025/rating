package id.ac.ui.cs.advprog.rating.mapper;

import id.ac.ui.cs.advprog.rating.dto.RatingRequest;
import id.ac.ui.cs.advprog.rating.dto.RatingResponse;
import id.ac.ui.cs.advprog.rating.model.Rating;

public class RatingMapper {

    public static Rating toEntity(RatingRequest request) {
        return Rating.builder()
                .consultationId(request.getConsultationId())
                .doctorId(request.getDoctorId())
                .userId(request.getUserId())
                .score(request.getScore())
                .comment(request.getComment())
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
