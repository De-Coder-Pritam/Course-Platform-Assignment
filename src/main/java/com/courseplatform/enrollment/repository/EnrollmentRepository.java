package com.courseplatform.enrollment.repository;

import com.courseplatform.enrollment.entity.Enrollment;
import com.courseplatform.course.entity.Course;
import com.courseplatform.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByUserAndCourse(User user, Course course);

    @Query("""
    select e
    from Enrollment e
    join e.course c
    join c.topics t
    join t.subtopics s
    where e.user.id = :userId
    and s.id = :subtopicId
""")
    Optional<Enrollment> findEnrollmentByUserIdAndSubtopicId(
            Long userId,
            String subtopicId
    );

    List<Enrollment> findByUserId(Long userId);

}
