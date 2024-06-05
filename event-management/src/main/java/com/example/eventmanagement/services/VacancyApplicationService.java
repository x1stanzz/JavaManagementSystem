package com.example.eventmanagement.services;

import com.example.eventmanagement.enums.ApplicationStatus;
import com.example.eventmanagement.models.Course;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.models.Vacancy;
import com.example.eventmanagement.models.VacancyApplication;
import com.example.eventmanagement.repositories.VacancyApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyApplicationService {
    private final VacancyApplicationRepository vacancyApplicationRepository;

    public List<VacancyApplication> getAllApplications() {
        return vacancyApplicationRepository.findAll();
    }

    public List<VacancyApplication> getApplicationsByUser(User user) {
        return vacancyApplicationRepository.findByUser(user);
    }

    public List<VacancyApplication> getApplicationsByVacancy(Vacancy vacancy) {
        return vacancyApplicationRepository.findByVacancy(vacancy);
    }

    public VacancyApplication applyToVacancy(User user, Vacancy vacancy) {
        VacancyApplication application = new VacancyApplication();
        application.setUser(user);
        application.setVacancy(vacancy);
        application.setStatus(ApplicationStatus.PENDING);
        return vacancyApplicationRepository.save(application);
    }

    public VacancyApplication getApplicationById(Long id) {
        return vacancyApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid application"));
    }

    public void updateApplicationStatus(Long id, ApplicationStatus status) {
        VacancyApplication application = getApplicationById(id);
        application.setStatus(status);
        vacancyApplicationRepository.save(application);
    }

    public void deleteApplication(Long id) {
        vacancyApplicationRepository.deleteById(id);
    }

    public boolean hasApplied(User user, Vacancy vacancy) {
        return vacancyApplicationRepository .existsByUserAndVacancy(user, vacancy);
    }
}
