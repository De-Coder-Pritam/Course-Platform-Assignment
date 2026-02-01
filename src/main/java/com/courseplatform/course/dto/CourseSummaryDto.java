package com.courseplatform.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseSummaryDto {

    private String id;
    private String title;
    private String description;
    private int topicCount;
    private int subtopicCount;
}
