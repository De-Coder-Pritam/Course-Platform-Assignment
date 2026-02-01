package com.courseplatform.progress.repository;

import com.courseplatform.progress.entity.SubtopicProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubtopicProgressRepository
        extends JpaRepository<SubtopicProgress, Long> {

    Optional<SubtopicProgress> findByEnrollmentIdAndSubtopicId(
            Long enrollmentId,
            String subtopicId
    );

    List<SubtopicProgress> findAllByEnrollmentId(Long enrollmentId);

}
