package com.example.coursesearch.controller;

import com.example.coursesearch.document.CourseDocument;
import com.example.coursesearch.service.CourseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class CourseSearchController {
    @Autowired private CourseSearchService service;

    @GetMapping
    public Map<String, Object> search(
        @RequestParam(required = false) String q,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Integer minAge,
        @RequestParam(required = false) Integer maxAge,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) String startDate,
        @RequestParam(defaultValue = "upcoming") String sort,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        SearchHits<CourseDocument> hits = service.searchCourses(
            q, category, type, minAge, maxAge, minPrice, maxPrice, startDate, sort, page, size
        );

        List<CourseDocument> results = hits.getSearchHits().stream()
            .map(SearchHit::getContent).collect(Collectors.toList());

        return Map.of(
            "total", hits.getTotalHits(),
            "courses", results
        );
    }
}