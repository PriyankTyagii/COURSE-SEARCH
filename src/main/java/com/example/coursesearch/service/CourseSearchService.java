package com.example.coursesearch.service;

import com.example.coursesearch.document.CourseDocument;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

@Service
public class CourseSearchService {
    @Autowired private ElasticsearchRestTemplate template;

    public SearchHits<CourseDocument> searchCourses(String q, String category, String type,
        Integer minAge, Integer maxAge, Double minPrice, Double maxPrice,
        String startDate, String sort, int page, int size) {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (q != null && !q.isEmpty()) {
            boolQuery.must(QueryBuilders.multiMatchQuery(q, "title", "description"));
        }

        if (category != null) boolQuery.filter(QueryBuilders.termQuery("category.keyword", category));
        if (type != null) boolQuery.filter(QueryBuilders.termQuery("type.keyword", type));
        if (minAge != null) boolQuery.filter(QueryBuilders.rangeQuery("minAge").gte(minAge));
        if (maxAge != null) boolQuery.filter(QueryBuilders.rangeQuery("maxAge").lte(maxAge));
        if (minPrice != null) boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice));
        if (maxPrice != null) boolQuery.filter(QueryBuilders.rangeQuery("price").lte(maxPrice));
        if (startDate != null) boolQuery.filter(QueryBuilders.rangeQuery("nextSessionDate").gte(startDate));

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(PageRequest.of(page, size));

        if ("priceAsc".equals(sort)) {
            queryBuilder.withSort(Sort.by("price").ascending());
        } else if ("priceDesc".equals(sort)) {
            queryBuilder.withSort(Sort.by("price").descending());
        } else {
            queryBuilder.withSort(Sort.by("nextSessionDate").ascending());
        }

        return template.search(queryBuilder.build(), CourseDocument.class);
    }
}