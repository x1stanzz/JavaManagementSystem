package com.example.eventmanagement.servicetest;

import com.example.eventmanagement.models.User;
import com.example.eventmanagement.models.Vacancy;
import com.example.eventmanagement.repositories.VacancyRepository;
import com.example.eventmanagement.services.UserService;
import com.example.eventmanagement.services.VacancyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class VacancyServiceTest {

    @Autowired
    private VacancyService vacancyService;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private UserService userService;

    @Test
    void createVacancy_ShouldReturnSavedVacancy() {
        // Arrange
        User user = userService.createUser(new User());
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle("Software Engineer");
        vacancy.setDescription("We are looking for a skilled software engineer.");

        // Act
        Vacancy savedVacancy = vacancyService.createVacancy(vacancy);

        // Assert
        assertThat(savedVacancy.getId()).isNotNull();
        assertThat(savedVacancy.getTitle()).isEqualTo("Software Engineer");
        assertThat(savedVacancy.getDescription()).isEqualTo("We are looking for a skilled software engineer.");
    }

    @Test
    void getVacancyById_ShouldReturnVacancy() {
        // Arrange
        User user = userService.createUser(new User());
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle("Software Engineer");
        vacancyRepository.save(vacancy);

        // Act
        Vacancy foundVacancy = vacancyService.getVacancyById(vacancy.getId());

        // Assert
        assertThat(foundVacancy.getId()).isEqualTo(vacancy.getId());
        assertThat(foundVacancy.getTitle()).isEqualTo("Software Engineer");
    }

    @Test
    void getVacancyById_ShouldThrowException_WhenVacancyNotFound() {
        // Act & Assert
        assertThatThrownBy(() -> vacancyService.getVacancyById(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Vacancy not found");
    }

    @Test
    void getAllVacancies_ShouldReturnAllVacancies() {
        // Arrange
        User user = userService.createUser(new User());
        Vacancy vacancy1 = new Vacancy();
        vacancy1.setTitle("Software Engineer");
        vacancyRepository.save(vacancy1);

        Vacancy vacancy2 = new Vacancy();
        vacancy2.setTitle("UI Designer");
        vacancyRepository.save(vacancy2);

        // Act
        List<Vacancy> vacancies = vacancyService.getAllVacancies();

        // Assert
        assertThat(vacancies).hasSize(2);
        assertThat(vacancies).extracting(Vacancy::getTitle)
                .containsExactlyInAnyOrder("Software Engineer", "UI Designer");
    }

    @AfterEach
    void cleanUp() {
        vacancyRepository.deleteAll();
    }
}
