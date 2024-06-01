package com.example.eventmanagement.controllers;

import com.example.eventmanagement.models.Course;
import com.example.eventmanagement.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/courses")
    public String getAllCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "course/list";
    }

    @GetMapping("/courses/{id}")
    public String getCourseById(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "course/view";
    }

    @GetMapping("/courses/create")
    public String createCourse(@ModelAttribute Course course) {
        courseService.createCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String showEditCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "course/edit";
    }

    @PostMapping("/courses/edit/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute Course course) {
        course.setId(id);
        courseService.createCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
