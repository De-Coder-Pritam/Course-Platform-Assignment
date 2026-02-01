package com.courseplatform.seed.dto;

import lombok.Data;
import java.util.List;

@Data
public class TopicSeedDto {
    private String id;
    private String title;
    private List<SubtopicSeedDto> subtopics;
}
