package com.company.hospitalsystem.controller;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.company.hospitalsystem.model.Patient;
import com.company.hospitalsystem.repository.DoctorRepository;
import com.company.hospitalsystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Redirect the base URL to page 1
    @GetMapping("/")
    public String viewHomePage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        return findPaginated(1, keyword, model);
    }

    // Handle pagination AND our new KPI data
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                Model model) {
        int pageSize = 5;
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Patient> page;

        if (keyword != null && !keyword.isEmpty()) {
            page = patientRepository.search(keyword, pageable);
        } else {
            page = patientRepository.findAll(pageable);
        }

        // Pagination Data
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("listPatients", page.getContent());

        // KPI Data (Count total doctors in the system)
        model.addAttribute("totalDoctors", doctorRepository.count());

        return "index";
    }

    // --- NEW ROUTE: View Patient Profile ---
    @GetMapping("/viewPatient/{id}")
    public String viewPatient(@PathVariable(value = "id") long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id: " + id));
        model.addAttribute("patient", patient);
        return "view_patient";
    }

    @GetMapping("/showNewPatientForm")
    public String showNewPatientForm(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        model.addAttribute("listDoctors", doctorRepository.findAll());
        return "new_patient";
    }

    // Save the patient with Validation
    @PostMapping("/savePatient")
    public String savePatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult bindingResult, Model model) {

        // 1. Check if the form has validation errors (e.g. negative age, blank name)
        if (bindingResult.hasErrors()) {
            // We must re-send the doctors list, otherwise the dropdown will crash on reload!
            model.addAttribute("listDoctors", doctorRepository.findAll());

            // If the patient has an ID, they were on the Update form. Otherwise, New form.
            if (patient.getId() != null) {
                return "update_patient";
            } else {
                return "new_patient";
            }
        }

        // 2. If no errors, save to database
        patientRepository.save(patient);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id: " + id));
        model.addAttribute("patient", patient);
        model.addAttribute("listDoctors", doctorRepository.findAll());
        return "update_patient";
    }

    @GetMapping("/deletePatient/{id}")
    public String deletePatient(@PathVariable(value = "id") long id) {
        patientRepository.deleteById(id);
        return "redirect:/";
    }
}