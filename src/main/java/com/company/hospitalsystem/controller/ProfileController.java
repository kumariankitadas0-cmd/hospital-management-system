package com.company.hospitalsystem.controller;

import com.company.hospitalsystem.model.User;
import com.company.hospitalsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Show the profile page
    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // principal.getName() gets the email of the currently logged-in user
        String email = principal.getName();
        User currentUser = userRepository.findByEmail(email);

        model.addAttribute("user", currentUser);
        return "profile";
    }

    // Handle the password change
    @PostMapping("/profile/updatePassword")
    public String updatePassword(@RequestParam("newPassword") String newPassword, Principal principal) {
        String email = principal.getName();
        User currentUser = userRepository.findByEmail(email);

        // Encrypt the new password and save it
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);

        return "redirect:/profile?success";
    }
}