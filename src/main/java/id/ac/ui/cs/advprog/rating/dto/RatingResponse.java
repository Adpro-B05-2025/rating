package id.ac.ui.cs.advprog.rating.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponse {
    private UUID id;
    private UUID consultationId;
    private UUID doctorId;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;
}
