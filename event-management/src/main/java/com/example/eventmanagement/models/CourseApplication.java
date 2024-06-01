package com.example.eventmanagement.models;

import com.example.eventmanagement.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}