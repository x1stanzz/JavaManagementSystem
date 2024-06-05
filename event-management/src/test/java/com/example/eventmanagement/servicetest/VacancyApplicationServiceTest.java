package com.example.eventmanagement.servicetest;

import com.example.eventmanagement.enums.ApplicationStatus;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.models.Vacancy;
import com.example.eventmanagement.models.VacancyApplication;
import com.example.eventmanagement.repositories.VacancyApplicationRepository;
import com.example.eventmanagement.services.VacancyApplicationService;
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
public class VacancyApplicationServiceTest {
    @Mock
    private VacancyApplicationRepository vacancyApplicationRepository;

    @InjectMocks
    private VacancyApplicationService vacancyApplicationService;

    @Captor
    private ArgumentCaptor<VacancyApplication> applicationCaptor;

    @Test
    public void applyToVacancy_Success() {
        User user = new User();
        user.setId(1L);
        Vacancy vacancy = new Vacancy();
        vacancy.setId(1L);

        VacancyApplication applicationToCreate = new VacancyApplication();
        applicationToCreate.setUser(user);
        applicationToCreate.setVacancy(vacancy);
        applicationToCreate.setStatus(ApplicationStatus.PENDING);

        when(vacancyApplicationRepository.save(any(VacancyApplication.class))).thenReturn(applicationToCreate);

        VacancyApplication createdApplication = vacancyApplicationService.applyToVacancy(user, vacancy);

        assertNotNull(createdApplication);
        assertEquals(user, createdApplication.getUser());
        assertEquals(vacancy, createdApplication.getVacancy());
        assertEquals(ApplicationStatus.PENDING, createdApplication.getStatus());
    }

    @Test
    public void getAllApplications_Success() {
        List<VacancyApplication> mockApplications = new ArrayList<>();
        mockApplications.add(new VacancyApplication());
        mockApplications.add(new VacancyApplication());

        when(vacancyApplicationRepository.findAll()).thenReturn(mockApplications);

        List<VacancyApplication> allApplications = vacancyApplicationService.getAllApplications();

        assertNotNull(allApplications);
        assertEquals(2, allApplications.size());
    }

    @Test
    public void getAllApplicationsByUser_Success() {
        User user = new User();
        user.setId(1L);

        List<VacancyApplication> mockApplications = new ArrayList<>();
        mockApplications.add(new VacancyApplication());
        mockApplications.add(new VacancyApplication());

        when(vacancyApplicationRepository.findByUser(user)).thenReturn(mockApplications);

        List<VacancyApplication> userApplications = vacancyApplicationService.getApplicationsByUser(user);

        assertNotNull(userApplications);
        assertEquals(2, userApplications.size());
    }

    @Test
    public void deleteApplication_Success() {
        Long applicationId = 1L;

        vacancyApplicationService.deleteApplication(applicationId);

        verify(vacancyApplicationRepository, times(1)).deleteById(applicationId);
    }
}
