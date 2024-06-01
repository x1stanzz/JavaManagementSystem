package com.example.eventmanagement.repositories;

import com.example.eventmanagement.enums.VacancyStatus;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByStatus(VacancyStatus status);
    List<Vacancy> findByEmployer(User employer);
}
