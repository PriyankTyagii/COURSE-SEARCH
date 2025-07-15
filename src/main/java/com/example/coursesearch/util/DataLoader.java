package com.example.coursesearch.util;

import com.example.coursesearch.document.CourseDocument;
import com.example.coursesearch.repository.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired private CourseRepository courseRepository;
    @Value("classpath:sample-courses.json")
    private Resource resource;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<CourseDocument> courses = Arrays.asList(
            mapper.readValue(resource.getInputStream(), CourseDocument[].class)
        );
        courseRepository.saveAll(courses);
        System.out.println("✅ Courses indexed");
    }
}