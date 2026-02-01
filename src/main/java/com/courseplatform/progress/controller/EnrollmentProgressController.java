package com.courseplatform.progress.controller;

import com.courseplatform.auth.repository.UserRepository;
import com.courseplatform.common.exception.ResourceNotFoundException;
import com.courseplatform.progress.dto.EnrollmentProgressResponse;
import com.courseplatform.progress.service.ProgressService;
import com.courseplatform.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrollments")
public class EnrollmentProgressController {

    private final ProgressService progressService;
    private final UserRepository userRepository;

    @GetMapping("/{enrollmentId}/progress")
    public EnrollmentProgressResponse getProgress(
            @PathVariable Long enrollmentId
    ) {

        // 1️⃣ Get authenticated user email from SecurityContext
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        // 2️⃣ Load User from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return progressService.getProgress(enrollmentId, user);
    }
}
