package com.example.eventmanagement.services;

import com.example.eventmanagement.enums.VacancyStatus;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.models.Vacancy;
import com.example.eventmanagement.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyRepository vacancyRepository;

    public Vacancy createVacancy(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    public Vacancy getVacancyById(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacancy not found"));
    }

    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }

    public List<Vacancy> getOpenVacancies() {
        return vacancyRepository.findByStatus(VacancyStatus.OPEN);
    }

    public List<Vacancy> getVacanciesBeEmployer(User employer) {
        return vacancyRepository.findByEmployer(employer);
    }

    public void closeVacancy(Long id) {
        Vacancy vacancy = getVacancyById(id);
        vacancy.setStatus(VacancyStatus.CLOSED);
        vacancyRepository.save(vacancy);
    }
}