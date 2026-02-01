package com.courseplatform.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResultDto {

    private String courseId;
    private String courseTitle;
    private List<SearchMatchDto> matches;
}
