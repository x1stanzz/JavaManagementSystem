package com.example.eventmanagement.servicetest;

import com.example.eventmanagement.models.Course;
import com.example.eventmanagement.repositories.CourseRepository;
import com.example.eventmanagement.services.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Captor
    private ArgumentCaptor<Course> courseCaptor;

    @Test
    public void createCourse_Success() {
        Course courseToCreate = new Course();
        courseToCreate.setName("Test Course");

        Course savedCourse = new Course();
        savedCourse.setId(1L);
        savedCourse.setName("Test Course");

        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        Course createdCourse = courseService.createCourse(courseToCreate);

        assertNotNull(createdCourse);
        assertEquals(savedCourse.getId(), createdCourse.getId());
        assertEquals(savedCourse.getName(), createdCourse.getName());

        verify(courseRepository, times(1)).save(courseCaptor.capture());
        Course capturedCourse = courseCaptor.getValue();
        assertEquals(courseToCreate.getName(), capturedCourse.getName());
    }

    @Test
    public void getCourseById_Success() {
        Long courseId = 1L;
        Course course = new Course();
        course.setId(courseId);
        course.setName("Test Course");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course retrievedCourse = courseService.getCourseById(courseId);

        assertNotNull(retrievedCourse);
        assertEquals(course.getId(), retrievedCourse.getId());
        assertEquals(course.getName(), retrievedCourse.getName());
    }

    @Test
    public void getAllCourses_Success() {
        List<Course> testCourses = new ArrayList<>();
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Course1");
        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Course 2");
        testCourses.add(course1);
        testCourses.add(course2);

        when(courseRepository.findAll()).thenReturn(testCourses);

        List<Course> allCourses = courseService.getAllCourses();

        assertNotNull(allCourses);
        assertEquals(2, allCourses.size());
        assertEquals("Course 1", allCourses.get(0).getName());
        assertEquals("Course 2", allCourses.get(1).getName());
    }

    @Test
    public void deleteCourse_Success() {
        Long courseId = 1L;
        courseService.deleteCourse(courseId);

        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    public void getCourseById_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            courseService.getCourseById(1L);
        });
    }

    @Test
    public void deleteCourse_NotFound() {
        Long courseId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(courseRepository).deleteById(courseId);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            courseService.deleteCourse(courseId);
        });
    }
}
