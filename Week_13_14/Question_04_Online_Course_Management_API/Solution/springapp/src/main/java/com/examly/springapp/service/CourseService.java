package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.CourseRequest;
import com.examly.springapp.model.Course;

public interface CourseService {

    List<Course> getAllCourses();

    Course addCourse(String createdBy, CourseRequest request);

    void deleteCourse(Long id);
}
