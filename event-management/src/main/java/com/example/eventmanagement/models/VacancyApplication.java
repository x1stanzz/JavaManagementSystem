package com.example.eventmanagement.models;

import com.example.eventmanagement.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vacancy_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
