package com.example.eventmanagement.models;

import com.example.eventmanagement.enums.VacancyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vacancies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String salary;
    @Enumerated(EnumType.STRING)
    private VacancyStatus status;
    @ManyToOne
    private User employer;
}
