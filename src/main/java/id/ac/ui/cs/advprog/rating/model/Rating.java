package id.ac.ui.cs.advprog.rating.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID consultationId;

    @Column(nullable = false)
    private UUID doctorId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Integer score;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Rating(UUID consultationId, UUID doctorId, UUID userId, Integer score, String comment) {
        this.consultationId = consultationId;
        this.doctorId = doctorId;
        this.userId = userId;
        this.score = score;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }
}
