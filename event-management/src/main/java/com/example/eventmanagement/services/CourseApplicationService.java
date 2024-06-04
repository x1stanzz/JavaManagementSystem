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
    private final CourseApplicationRepository courseApplicationRepository;

    public List<CourseApplication> getAllApplications() {
        return courseApplicationRepository.findAll();
    }

    public List<CourseApplication> getApplicationsByUser(User user) {
        return courseApplicationRepository.findByUser(user);
    }

    public List<CourseApplication> getApplicationsByCourse(Course course) {
        return courseApplicationRepository.findByCourse(course);
    }

    public CourseApplication applyToCourse(User user, Course course) {
        CourseApplication application = new CourseApplication();
        application.setUser(user);
        application.setCourse(course);
        application.setStatus(ApplicationStatus.PENDING); // При создании заявки ее статус будет "В ожидании"
        return courseApplicationRepository.save(application);
    }

    public CourseApplication getApplicationById(Long id) {
        return courseApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid application ID"));
    }

    public void updateApplicationStatus(Long id, ApplicationStatus status) {
        CourseApplication application = getApplicationById(id);
        application.setStatus(status);
        courseApplicationRepository.save(application);
    }

    public void deleteApplication(Long id) {
        courseApplicationRepository.deleteById(id);
    }
}
