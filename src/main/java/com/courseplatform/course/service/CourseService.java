package com.courseplatform.course.service;

import com.courseplatform.common.exception.ResourceNotFoundException;
import com.courseplatform.course.dto.*;
import com.courseplatform.course.entity.Course;
import com.courseplatform.course.entity.Topic;
import com.courseplatform.course.entity.Subtopic;
import com.courseplatform.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    // 1️⃣ List all courses (summary)
    public List<CourseSummaryDto> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(course -> {

                    int topicCount = course.getTopics().size();

                    int subtopicCount = course.getTopics().stream()
                            .mapToInt(topic -> topic.getSubtopics().size())
                            .sum();

                    return new CourseSummaryDto(
                            course.getId(),
                            course.getTitle(),
                            course.getDescription(),
                            topicCount,
                            subtopicCount
                    );
                })
                .toList();
    }

    // 2️⃣ Get full course by ID
    public CourseDetailDto getCourseById(String courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course with id '" + courseId + "' does not exist"
                        )
                );

        List<TopicDto> topicDtos = course.getTopics()
                .stream()
                .map(this::mapToTopicDto)
                .toList();

        return new CourseDetailDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                topicDtos
        );
    }

    private TopicDto mapToTopicDto(Topic topic) {

        List<SubtopicDto> subtopicDtos = topic.getSubtopics()
                .stream()
                .map(this::mapToSubtopicDto)
                .toList();

        return new TopicDto(
                topic.getId(),
                topic.getTitle(),
                subtopicDtos
        );
    }

    private SubtopicDto mapToSubtopicDto(Subtopic subtopic) {
        return new SubtopicDto(
                subtopic.getId(),
                subtopic.getTitle(),
                subtopic.getContent()
        );
    }
}
