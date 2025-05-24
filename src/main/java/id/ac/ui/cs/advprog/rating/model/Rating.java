package id.ac.ui.cs.advprog.rating.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rating")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long consultationId;

    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer score;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Rating(Long consultationId, Long doctorId, Long userId, Integer score, String comment) {
        this.consultationId = consultationId;
        this.doctorId = doctorId;
        this.userId = userId;
        this.score = score;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }
}
