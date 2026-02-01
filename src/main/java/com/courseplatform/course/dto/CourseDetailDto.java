package com.courseplatform.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CourseDetailDto {

    private String id;
    private String title;
    private String description;
    private List<TopicDto> topics;
}

