package com.example.eventmanagement.repositories;

import com.example.eventmanagement.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
