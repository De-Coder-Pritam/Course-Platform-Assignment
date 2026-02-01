package com.courseplatform.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class EnrollmentResponse {

    private Long enrollmentId;
    private String courseId;
    private String courseTitle;
    private Instant enrolledAt;
}
