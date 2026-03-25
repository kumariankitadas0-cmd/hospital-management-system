package com.company.hospitalsystem.controller;

import com.company.hospitalsystem.model.Doctor;
import com.company.hospitalsystem.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    // View the Doctor Directory
    @GetMapping("/doctors")
    public String viewDoctorDirectory(Model model) {
        model.addAttribute("listDoctors", doctorRepository.findAll());
        return "doctors";
    }

    // Show form to add a new doctor
    @GetMapping("/showNewDoctorForm")
    public String showNewDoctorForm(Model model) {
        Doctor doctor = new Doctor();
        model.addAttribute("doctor", doctor);
        return "new_doctor";
    }

    // Save the doctor to the database
    @PostMapping("/saveDoctor")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }

    // Delete a doctor (Only Admins can do this, protected by SecurityConfig)
    @GetMapping("/deleteDoctor/{id}")
    public String deleteDoctor(@PathVariable(value = "id") long id) {
        doctorRepository.deleteById(id);
        return "redirect:/doctors";
    }
}