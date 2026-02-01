package com.courseplatform.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TopicDto {

    private String id;
    private String title;
    private List<SubtopicDto> subtopics;
}
