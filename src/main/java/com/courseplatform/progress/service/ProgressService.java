package com.courseplatform.progress.service;

import com.courseplatform.common.exception.ForbiddenException;
import com.courseplatform.common.exception.ResourceNotFoundException;
import com.courseplatform.enrollment.entity.Enrollment;
import com.courseplatform.enrollment.repository.EnrollmentRepository;
import com.courseplatform.progress.dto.CompletedSubtopicDto;
import com.courseplatform.progress.dto.EnrollmentProgressResponse;
import com.courseplatform.progress.dto.SubtopicCompletionResponse;
import com.courseplatform.progress.entity.SubtopicProgress;
import com.courseplatform.progress.repository.SubtopicProgressRepository;
import com.courseplatform.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final EnrollmentRepository enrollmentRepository;
    private final SubtopicProgressRepository progressRepository;

    @Transactional
    public SubtopicCompletionResponse markCompleted(
            String subtopicId,
            User user
    ) {

        // 1️⃣ Find enrollment that owns this subtopic
        Enrollment enrollment = enrollmentRepository
                .findEnrollmentByUserIdAndSubtopicId(
                        user.getId(),
                        subtopicId
                )
                .orElseThrow(() ->
                        new ForbiddenException(
                                "You must be enrolled in this course to mark subtopics as complete"
                        )
                );

        // 2️⃣ Check idempotency
        return progressRepository
                .findByEnrollmentIdAndSubtopicId(
                        enrollment.getId(),
                        subtopicId
                )
                .map(progress ->
                        new SubtopicCompletionResponse(
                                subtopicId,
                                true,
                                progress.getCompletedAt()
                        )
                )
                // 3️⃣ Create progress if not exists
                .orElseGet(() -> {
                    SubtopicProgress progress = new SubtopicProgress();
                    progress.setEnrollment(enrollment);
                    progress.setSubtopicId(subtopicId);

                    SubtopicProgress saved =
                            progressRepository.save(progress);

                    return new SubtopicCompletionResponse(
                            subtopicId,
                            true,
                            saved.getCompletedAt()
                    );
                });
    }

    @Transactional(readOnly = true)
    public EnrollmentProgressResponse getProgress(
            Long enrollmentId,
            User user
    ) {

        Enrollment enrollment = enrollmentRepository
                .findById(enrollmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Enrollment not found"));

        // 🔐 Ownership check
        if (!enrollment.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("You are not allowed to view this enrollment");
        }

        // 📘 Course data
        var course = enrollment.getCourse();

        // 🔢 Total subtopics
        int totalSubtopics = course.getTopics().stream()
                .mapToInt(t -> t.getSubtopics().size())
                .sum();

        // ✅ Completed subtopics
        var completedProgress =
                progressRepository.findAllByEnrollmentId(enrollmentId);

        var completedItems = completedProgress.stream()
                .map(p -> {
                    var subtopic = course.getTopics().stream()
                            .flatMap(t -> t.getSubtopics().stream())
                            .filter(s -> s.getId().equals(p.getSubtopicId()))
                            .findFirst()
                            .orElse(null);

                    return new CompletedSubtopicDto(
                            p.getSubtopicId(),
                            subtopic != null ? subtopic.getTitle() : "",
                            p.getCompletedAt()
                    );
                })
                .toList();

        int completedCount = completedItems.size();

        double percentage =
                totalSubtopics == 0
                        ? 0
                        : (completedCount * 100.0) / totalSubtopics;

        return new EnrollmentProgressResponse(
                enrollment.getId(),
                course.getId(),
                course.getTitle(),
                totalSubtopics,
                completedCount,
                Math.round(percentage * 100.0) / 100.0,
                completedItems
        );
    }

}
