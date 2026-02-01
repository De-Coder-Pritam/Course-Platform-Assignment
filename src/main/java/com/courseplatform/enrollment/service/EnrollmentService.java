package com.courseplatform.enrollment.service;

import com.courseplatform.common.exception.ConflictException;
import com.courseplatform.common.exception.ResourceNotFoundException;
import com.courseplatform.course.entity.Course;
import com.courseplatform.course.repository.CourseRepository;
import com.courseplatform.enrollment.dto.EnrollmentResponse;
import com.courseplatform.enrollment.dto.EnrollmentSummaryResponse;
import com.courseplatform.enrollment.entity.Enrollment;
import com.courseplatform.enrollment.repository.EnrollmentRepository;
import com.courseplatform.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public EnrollmentResponse enroll(String courseId, User user) {

        // 1️⃣ Check course existence
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course with id '" + courseId + "' does not exist"
                        )
                );

        // 2️⃣ Prevent duplicate enrollment
        if (enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new ConflictException(
                    "You are already enrolled in this course"
            );
        }

        // 3️⃣ Create enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(Instant.now());

        Enrollment saved = enrollmentRepository.save(enrollment);

        return new EnrollmentResponse(
                saved.getId(),
                course.getId(),
                course.getTitle(),
                saved.getEnrolledAt()
        );
    }

    @Transactional(readOnly = true)
    public List<EnrollmentSummaryResponse> getMyEnrollments(User user) {

        return enrollmentRepository
                .findByUserId(user.getId())
                .stream()
                .map(e -> new EnrollmentSummaryResponse(
                        e.getId(),
                        e.getCourse().getId(),
                        e.getCourse().getTitle(),
                        e.getEnrolledAt()
                ))
                .toList();
    }

}
