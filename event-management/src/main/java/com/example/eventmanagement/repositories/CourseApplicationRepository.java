package com.example.eventmanagement.repositories;

import com.example.eventmanagement.models.Course;
import com.example.eventmanagement.models.CourseApplication;
import com.example.eventmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseApplicationRepository extends JpaRepository<CourseApplication, Long> {
    List<CourseApplication> findByUser(User user);
    List<CourseApplication> findByCourse(Course course);
    boolean existsByUserAndCourse(User user, Course course);
}
