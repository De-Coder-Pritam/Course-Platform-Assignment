package com.courseplatform.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class EnrollmentSummaryResponse {

    private Long enrollmentId;
    private String courseId;
    private String courseTitle;
    private Instant enrolledAt;
}
