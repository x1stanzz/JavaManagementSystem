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
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;
    @Column(name = "course_id", insertable = false, updatable = false)
    private Long courseId;
}