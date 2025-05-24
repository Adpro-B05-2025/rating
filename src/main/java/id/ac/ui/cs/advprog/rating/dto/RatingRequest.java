package id.ac.ui.cs.advprog.rating.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingRequest {
    private Long consultationId;
    private Long doctorId;
    private Integer score;
    private String comment;
}
