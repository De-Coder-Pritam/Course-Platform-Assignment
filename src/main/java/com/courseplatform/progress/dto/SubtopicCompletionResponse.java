package com.courseplatform.progress.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SubtopicCompletionResponse {

    private String subtopicId;
    private boolean completed;
    private Instant completedAt;
}
