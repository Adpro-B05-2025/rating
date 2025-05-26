package id.ac.ui.cs.advprog.rating.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponse {
    private Long id;
    private Long consultationId;
    private Long doctorId;
    private Long userId;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;
}
