package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.CourseRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Course;
import com.examly.springapp.repository.CourseRepository;
import com.examly.springapp.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course addCourse(String createdBy, CourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setCategory(request.getCategory());
        course.setDurationHours(request.getDurationHours());
        course.setCreatedBy(createdBy);
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}
