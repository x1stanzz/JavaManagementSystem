package com.example.eventmanagement.services;

import com.example.eventmanagement.enums.ApplicationStatus;
import com.example.eventmanagement.models.Course;
import com.example.eventmanagement.models.CourseApplication;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.repositories.CourseApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseApplicationService {
    private final CourseApplicationRepository applicationRepository;
    private final CourseService courseService;
    private final UserService userService;

    public CourseApplication applyForCourse(Long courseId, Long userId) {
        Course course = courseService.getCourseById(courseId);
        User user = userService.getUserById(userId);
        CourseApplication application = new CourseApplication();
        application.setCourse(course);
        application.setUser(user);
        application.setStatus(ApplicationStatus.PENDING);
        return applicationRepository.save(application);
    }

    public List<CourseApplication> getApplicationsByUser(Long userId) {
        User user = userService.getUserById(userId);
        return applicationRepository.findByUser(user);
    }

    public List<CourseApplication> getApplicationsByCourse(Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return applicationRepository.findByCourse(course);
    }

    public void approveApplication(Long applicationId) {
        CourseApplication application = getApplicationById(applicationId);
        application.setStatus(ApplicationStatus.APPROVED);
        applicationRepository.save(application);
    }

    public void rejectApplication(Long applicationId) {
        CourseApplication application = getApplicationById(applicationId);
        application.setStatus(ApplicationStatus.REJECTED);
        applicationRepository.save(application);
    }

    private CourseApplication getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
    }
}
