package com.courseplatform.progress.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CompletedSubtopicDto {

    private String subtopicId;
    private String subtopicTitle;
    private Instant completedAt;
}
