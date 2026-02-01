package com.courseplatform.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResponseDto {

    private String query;
    private List<SearchResultDto> results;
}
