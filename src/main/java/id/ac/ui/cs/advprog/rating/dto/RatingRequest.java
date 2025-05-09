package id.ac.ui.cs.advprog.rating.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingRequest {
    private UUID consultationId;
    private UUID doctorId;
    private UUID userId;
    private Integer score;
    private String comment;
}
