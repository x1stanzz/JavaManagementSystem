package com.example.eventmanagement.controllers;

import com.example.eventmanagement.enums.ApplicationStatus;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.models.Vacancy;
import com.example.eventmanagement.models.VacancyApplication;
import com.example.eventmanagement.services.UserService;
import com.example.eventmanagement.services.VacancyApplicationService;
import com.example.eventmanagement.services.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/vacancy-applications")
@RequiredArgsConstructor
public class VacancyApplicationController {
    private final VacancyApplicationService applicationService;
    private final UserService userService;
    private final VacancyService vacancyService;

    @PostMapping("/apply/{vacancyId}")
    public String applyToVacancy(@PathVariable Long vacancyId, Principal principal, RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        Vacancy vacancy = vacancyService.getVacancyById(vacancyId);
        if(applicationService.hasApplied(user, vacancy)) {
            redirectAttributes.addFlashAttribute("error", "You have already applied to this vacancy.");
            return "redirect:/vacancies";
        }
        applicationService.applyToVacancy(user, vacancy);
        return  "redirect:/vacancies";
    }

    @GetMapping("/manage")
    public String manageApplications(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        List<VacancyApplication> applications = applicationService.getApplicationsByUser(user);
        model.addAttribute("applications", applications);
        return "application/manage";
    }

    @PostMapping("/updateStatus/{id}")
    public String updateApplicationStatus(@PathVariable Long id, @RequestParam("status")ApplicationStatus status) {
        applicationService.updateApplicationStatus(id, status);
        return "redirect:/vacancy-applications/manage";
    }

    @PostMapping("/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return "redirect:/vacancy-applications/manage";
    }
}
