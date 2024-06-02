package com.example.eventmanagement.controllers;

import com.example.eventmanagement.models.Vacancy;
import com.example.eventmanagement.services.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public String getAllVacancies(Model model) {
        model.addAttribute("vacancies", vacancyService.getAllVacancies());
        return "vacancy/list";
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/create")
    public String showCreateVacancyFrom(Model model) {
        model.addAttribute("vacancy", new Vacancy());
        return "/vacancy/create";
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping("/create")
    public String createVacancy(@ModelAttribute Vacancy vacancy) {
        vacancyService.createVacancy(vacancy);
        return "redirect:/vacancies";
    }

    @GetMapping("/{id}")
    public String showVacancyDetails(@PathVariable Long id, Model model) {
        Vacancy vacancy = vacancyService.getVacancyById(id);
        model.addAttribute("vacancy", vacancy);
        return "vacancy/details";
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/edit/{id}")
    public String showEditVacancyForm(@PathVariable Long id, Model model) {
        Vacancy vacancy = vacancyService.getVacancyById(id);
        model.addAttribute("vacancy", vacancy);
        return "vacancy/edit";
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping("/edit/{id}")
    public String updateVacancy(@PathVariable Long id, @ModelAttribute Vacancy updatedVacancy) {
        vacancyService.updateVacancy(id, updatedVacancy);
        return "redirect:/vacancies";
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/delete/{id}")
    public String deleteVacancy(@PathVariable Long id) {
        vacancyService.deleteVacancy(id);
        return "redirect:/vacancies";
    }

}
