package com.example.eventmanagement.controllers;

import com.example.eventmanagement.enums.ApplicationStatus;
import com.example.eventmanagement.models.Course;
import com.example.eventmanagement.models.CourseApplication;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.services.CourseApplicationService;
import com.example.eventmanagement.services.CourseService;
import com.example.eventmanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class CourseApplicationController {
    private final CourseApplicationService applicationService;
    private final UserService userService;
    private final CourseService courseService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/apply/{courseId}")
    public String applyToCourse(@PathVariable Long courseId, Principal principal, RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        Course course = courseService.getCourseById(courseId);
        if(applicationService.hasApplied(user, course)) {
            redirectAttributes.addFlashAttribute("error", "You have already applied to this course.");
            return "redirect:/courses";
        }

        applicationService.applyToCourse(user, course);
        return "redirect:/courses";
    }

    @GetMapping("/manage")
    public String manageApplications(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        List<CourseApplication> applications = applicationService.getApplicationsByUser(user);
        model.addAttribute("applications", applications);
        return "application/manage";
    }

    @PostMapping("/updateStatus/{id}")
    public String updateApplicationStatus(@PathVariable Long id, @RequestParam("status") ApplicationStatus status) {
        applicationService.updateApplicationStatus(id, status);
        return "redirect:/applications/manage";
    }

    @PostMapping("/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return "redirect:/applications/manage";
    }
}
