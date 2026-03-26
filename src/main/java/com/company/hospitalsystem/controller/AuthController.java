package com.company.hospitalsystem.controller;

import com.company.hospitalsystem.model.Role;
import com.company.hospitalsystem.model.User;
import com.company.hospitalsystem.repository.RoleRepository;
import com.company.hospitalsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Show custom login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Save new user from registration form
    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") User user, Model model) {
        // Check if email already exists
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return "redirect:/register?error";
        }

        // Encrypt their password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Seed Admin with an EMAIL instead of username
        if (userRepository.findByEmail("admin@hospital.com") == null) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            // CHANGE List.of to Set.of here:
            User adminUser = new User("System Admin", "admin@hospital.com", passwordEncoder.encode("admin123"), java.util.Set.of(adminRole));
            userRepository.save(adminUser);
        }

        userRepository.save(user);
        return "redirect:/register?success";
    }
}