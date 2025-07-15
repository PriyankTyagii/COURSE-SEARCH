package com.example.coursesearch.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDocument {
    @Id private String id;
    private String title;
    private String description;
    private String category;
    private String type;
    private String gradeRange;
    private Integer minAge;
    private Integer maxAge;
    private Double price;
    private LocalDateTime nextSessionDate;
}