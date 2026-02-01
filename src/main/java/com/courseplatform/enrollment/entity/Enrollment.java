package com.courseplatform.enrollment.entity;

import com.courseplatform.course.entity.Course;
import com.courseplatform.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "course_id"}
        )
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, updatable = false)
    private Instant enrolledAt;

    @PrePersist
    public void prePersist() {
        this.enrolledAt = Instant.now();
    }
}
