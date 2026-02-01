package com.courseplatform.search.controller;

import com.courseplatform.search.dto.SearchResponseDto;
import com.courseplatform.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public SearchResponseDto search(@RequestParam("q") String query) {
        return searchService.search(query);
    }
}
