package com.courseplatform.progress.entity;

import com.courseplatform.enrollment.entity.Enrollment;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "subtopic_progress",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"enrollment_id", "subtopic_id"}
        )
)
public class SubtopicProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Progress belongs to a specific enrollment,
     * not directly to a user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    /**
     * Subtopic identifier (from seed data)
     */
    @Column(name = "subtopic_id", nullable = false)
    private String subtopicId;

    /**
     * Timestamp when subtopic was completed
     */
    @Column(nullable = false, updatable = false)
    private Instant completedAt;

    @PrePersist
    protected void onCreate() {
        this.completedAt = Instant.now();
    }
}
