package com.example.eventmanagement.servicetest;

import com.example.eventmanagement.enums.ApplicationStatus;
import com.example.eventmanagement.models.Course;
import com.example.eventmanagement.models.CourseApplication;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.repositories.CourseApplicationRepository;
import com.example.eventmanagement.services.CourseApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseApplicationServiceTest {
    @Mock
    private CourseApplicationRepository courseApplicationRepository;

    @InjectMocks
    private CourseApplicationService courseApplicationService;

    @Captor
    private ArgumentCaptor<CourseApplication> applicationArgumentCaptor;

    @Test
    public void applyToCourse_Success() {
        User user = new User();
        user.setId(1L);
        Course course = new Course();
        course.setId(1L);

        CourseApplication applicationToCreate = new CourseApplication();
        applicationToCreate.setUser(user);
        applicationToCreate.setCourse(course);
        applicationToCreate.setStatus(ApplicationStatus.PENDING);

        when(courseApplicationRepository.save(any(CourseApplication.class))).thenReturn(applicationToCreate);

        CourseApplication createdApplication = courseApplicationService.applyToCourse(user, course);

        assertNotNull(createdApplication);
        assertEquals(user, createdApplication.getUser());
        assertEquals(course, createdApplication.getCourse());
        assertEquals(ApplicationStatus.PENDING, createdApplication.getStatus());
    }

    @Test
    public void getAllApplications_Success() {
        List<CourseApplication> mockApplications = new ArrayList<>();
        mockApplications.add(new CourseApplication());
        mockApplications.add(new CourseApplication());

        when(courseApplicationRepository.findAll()).thenReturn(mockApplications);

        List<CourseApplication> applications = courseApplicationService.getAllApplications();

        assertNotNull(applications);
        assertEquals(2, applications.size());
    }

    @Test
    public void getApplicationsByUser_Success() {
        User user = new User();
        user.setId(1L);

        List<CourseApplication> mockApplications = new ArrayList<>();
        mockApplications.add(new CourseApplication());
        mockApplications.add(new CourseApplication());

        when(courseApplicationRepository.findByUser(user)).thenReturn(mockApplications);

        List<CourseApplication> userApplications = courseApplicationService.getApplicationsByUser(user);

        assertNotNull(userApplications);
        assertEquals(2, userApplications.size());
    }

    @Test
    public void deleteApplication_Success() {
        Long applicationId = 1L;

        courseApplicationService.deleteApplication(applicationId);

        verify(courseApplicationRepository, times(1)).deleteById(applicationId);
    }
}
