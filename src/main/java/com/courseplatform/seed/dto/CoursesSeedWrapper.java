package com.courseplatform.seed.dto;

import lombok.Data;
import java.util.List;

@Data
public class CoursesSeedWrapper {
    private List<CourseSeedDto> courses;
}
