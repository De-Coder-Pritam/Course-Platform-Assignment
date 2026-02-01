package com.courseplatform.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchMatchDto {

    private String type;          // "course" | "topic" | "subtopic" | "content"
    private String topicTitle;
    private String subtopicId;
    private String subtopicTitle;
    private String snippet;
}
