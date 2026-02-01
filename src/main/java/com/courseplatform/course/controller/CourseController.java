package com.courseplatform.course.controller;

import com.courseplatform.course.dto.CourseDetailDto;
import com.courseplatform.course.dto.CourseSummaryDto;
import com.courseplatform.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // ✅ Public: List all courses
    @GetMapping
    public List<CourseSummaryDto> getAllCourses() {
        return courseService.getAllCourses();
    }

    // ✅ Public: Get course by ID
    @GetMapping("/{courseId}")
    public CourseDetailDto getCourseById(@PathVariable String courseId) {
        return courseService.getCourseById(courseId);
    }
}
