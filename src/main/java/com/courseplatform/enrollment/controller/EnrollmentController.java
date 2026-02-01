package com.courseplatform.enrollment.controller;

import com.courseplatform.common.exception.ResourceNotFoundException;
import com.courseplatform.enrollment.dto.EnrollmentResponse;
import com.courseplatform.enrollment.dto.EnrollmentSummaryResponse;
import com.courseplatform.enrollment.entity.Enrollment;
import com.courseplatform.enrollment.service.EnrollmentService;
import com.courseplatform.user.entity.User;
import com.courseplatform.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;

    @PostMapping("/{courseId}/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse enroll(@PathVariable String courseId) {

        // 1️⃣ Get authenticated user email from SecurityContext
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        // 2️⃣ Load User from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // 3️⃣ Enroll
        return enrollmentService.enroll(courseId, user);
    }

    @GetMapping("/enrollments/me")
    public List<EnrollmentSummaryResponse> myEnrollments() {

        // 1️⃣ Get authenticated user email from SecurityContext
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        // 2️⃣ Load User from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return enrollmentService.getMyEnrollments(user);
    }

}
