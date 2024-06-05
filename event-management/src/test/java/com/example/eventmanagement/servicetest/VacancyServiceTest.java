package com.example.eventmanagement.servicetest;

import com.example.eventmanagement.models.Vacancy;
import com.example.eventmanagement.repositories.VacancyRepository;
import com.example.eventmanagement.services.VacancyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VacancyServiceTest {
    @Mock
    private VacancyRepository vacancyRepository;

    @InjectMocks
    private VacancyService vacancyService;

    @Captor
    private ArgumentCaptor<Vacancy> vacancyCaptor;

    @Test
    public void createVacancy_Success() {
        Vacancy vacancyToCreate = new Vacancy();
        vacancyToCreate.setTitle("Test Vacancy");

        Vacancy savedVacancy = new Vacancy();
        savedVacancy.setId(1L);
        savedVacancy.setTitle("Test Vacancy");

        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(savedVacancy);

        Vacancy createdVacancy = vacancyService.createVacancy(vacancyToCreate);

        assertNotNull(createdVacancy);
        assertEquals(savedVacancy.getId(), createdVacancy.getId());
        assertEquals(savedVacancy.getTitle(), createdVacancy.getTitle());
    }

    @Test
    public void getVacancyById_Success() {
        Long vacancyId = 1L;
        Vacancy testVacancy = new Vacancy();
        testVacancy.setId(vacancyId);
        testVacancy.setTitle("Test Vacancy");

        when(vacancyRepository.findById(vacancyId)).thenReturn(Optional.of(testVacancy));

        Vacancy foundVacancy = vacancyService.getVacancyById(vacancyId);

        assertNotNull(foundVacancy);
        assertEquals(vacancyId, foundVacancy.getId());
        assertEquals("Test Vacancy", foundVacancy.getTitle());
    }

    @Test
    public void deleteVacancy_NotFound() {
        Long vacancyId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(vacancyRepository).deleteById(vacancyId);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            vacancyService.deleteVacancy(vacancyId);
        });
    }
}
