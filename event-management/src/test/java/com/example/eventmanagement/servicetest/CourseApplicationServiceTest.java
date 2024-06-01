package com.example.eventmanagement.servicetest;

import com.example.eventmanagement.enums.ApplicationStatus;
import com.example.eventmanagement.models.Course;
import com.example.eventmanagement.models.CourseApplication;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.repositories.CourseApplicationRepository;
import com.example.eventmanagement.services.CourseApplicationService;
import com.example.eventmanagement.services.CourseService;
import com.example.eventmanagement.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CourseApplicationServiceTest {

    @Autowired
    private CourseApplicationService applicationService;

    @Autowired
    private CourseApplicationRepository applicationRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Test
    void applyForCourse_ShouldReturnSavedApplication() {
        Course course = courseService.createCourse(new Course());
        User user = userService.createUser(new User());

        CourseApplication application = applicationService.applyForCourse(course.getId(), user.getId());

        assertThat(application.getId()).isNotNull();
        assertThat(application.getCourse().getId()).isEqualTo(course.getId());
        assertThat(application.getUser().getId()).isEqualTo(user.getId());
        assertThat(application.getStatus()).isEqualTo(ApplicationStatus.PENDING);
    }

    @Test
    void getApplicationsByUser_ShouldReturnApplications() {
        Course course = courseService.createCourse(new Course());
        User user = userService.createUser(new User());
        CourseApplication application = applicationService.applyForCourse(course.getId(), user.getId());

        List<CourseApplication> applications = applicationService.getApplicationsByUser(user.getId());

        assertThat(applications).hasSize(1);
        assertThat(applications.get(0).getId()).isEqualTo(application.getId());
    }

    @Test
    void approveApplication_ShouldChangeApplicationStatus() {
        // Arrange
        Course course = courseService.createCourse(new Course());
        User user = userService.createUser(new User());
        CourseApplication application = applicationService.applyForCourse(course.getId(), user.getId());

        applicationService.approveApplication(application.getId());
        CourseApplication updatedApplication = applicationRepository.findById(application.getId()).get();

        assertThat(updatedApplication.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
    }

    @AfterEach
    void cleanUp() {
        applicationRepository.deleteAll();
    }
}
