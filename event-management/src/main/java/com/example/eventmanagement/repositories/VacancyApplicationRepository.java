package com.example.eventmanagement.repositories;

import com.example.eventmanagement.models.User;
import com.example.eventmanagement.models.Vacancy;
import com.example.eventmanagement.models.VacancyApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacancyApplicationRepository extends JpaRepository<VacancyApplication, Long> {
    List<VacancyApplication> findByUser(User user);
    List<VacancyApplication> findByVacancy(Vacancy vacancy);
    boolean existsByUserAndVacancy(User user, Vacancy vacancy);
}
