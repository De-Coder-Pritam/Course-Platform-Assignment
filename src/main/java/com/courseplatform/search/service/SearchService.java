package com.courseplatform.search.service;

import com.courseplatform.search.dto.*;
import com.courseplatform.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchResponseDto search(String query) {

        List<Object[]> rows = searchRepository.search(query);

        Map<String, SearchResultDto> resultMap = new LinkedHashMap<>();

        for (Object[] row : rows) {

            String courseId = (String) row[0];
            String courseTitle = (String) row[1];
            String topicTitle = (String) row[2];
            String subtopicId = (String) row[3];
            String subtopicTitle = (String) row[4];
            String content = (String) row[5];

            SearchMatchDto match = new SearchMatchDto(
                    "subtopic",
                    topicTitle,
                    subtopicId,
                    subtopicTitle,
                    buildSnippet(content, query)
            );

            resultMap
                    .computeIfAbsent(courseId,
                            id -> new SearchResultDto(id, courseTitle, new ArrayList<>()))
                    .getMatches()
                    .add(match);
        }

        return new SearchResponseDto(
                query,
                new ArrayList<>(resultMap.values())
        );
    }

    private String buildSnippet(String content, String query) {

        String lower = content.toLowerCase();
        String q = query.toLowerCase();

        int index = lower.indexOf(q);
        if (index == -1) {
            return content.substring(0, Math.min(120, content.length())) + "...";
        }

        int start = Math.max(0, index - 40);
        int end = Math.min(content.length(), index + q.length() + 40);

        return "..." + content.substring(start, end) + "...";
    }
}
