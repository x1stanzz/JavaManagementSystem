package com.example.eventmanagement.servicetest;

import com.example.eventmanagement.models.Course;
import com.example.eventmanagement.repositories.CourseRepository;
import com.example.eventmanagement.services.CourseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void createCourse_ShouldReturnSavedCourse() {
        Course course = new Course();
        course.setName("Test Course");
        course.setDescription("This is a test course");
        course.setDuration(45);
        course.setPrice(1000.0);
        course.setAvailable(true);

        Course savedCourse = courseService.createCourse(course);

        assertThat(savedCourse.getId()).isNotNull();
        assertThat(savedCourse.getName()).isEqualTo("Test Course");
        assertThat(savedCourse.getDescription()).isEqualTo("This is a test course");
        assertThat(savedCourse.getDuration()).isEqualTo(45);
        assertThat(savedCourse.getPrice()).isEqualTo(1000.0);
        assertThat(savedCourse.getAvailable()).isEqualTo(true);
    }

    @Test
    void getCourseById_ShouldReturnCourse() {
        Course course = new Course();
        course.setName("Test Course");
        courseRepository.save(course);

        Course foundCourse = courseService.getCourseById(course.getId());

        assertThat(foundCourse.getId()).isEqualTo(course.getId());
        assertThat(foundCourse.getName()).isEqualTo("Test Course");
    }

    @Test
    void getCourseById_ShouldThrowException_WhenCourseNotFound() {
        assertThatThrownBy(() -> courseService.getCourseById(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Course not found");
    }

    @AfterEach
    void cleanUp() {
        courseRepository.deleteAll();
    }
}
