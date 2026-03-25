package com.company.hospitalsystem.config;

import com.company.hospitalsystem.model.Doctor;
import com.company.hospitalsystem.model.Role;
import com.company.hospitalsystem.model.User;
import com.company.hospitalsystem.repository.DoctorRepository;
import com.company.hospitalsystem.repository.RoleRepository;
import com.company.hospitalsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(DoctorRepository doctorRepository, UserRepository userRepository,
                                      RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (doctorRepository.count() == 0) {
                doctorRepository.saveAll(List.of(
                        new Doctor("Dr. Sarah Smith", "Cardiology"),
                        new Doctor("Dr. John Doe", "Neurology"),
                        new Doctor("Dr. Emily Chen", "General Medicine")
                ));
            }
            if (roleRepository.findByName("ROLE_ADMIN") == null) {
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
            if (roleRepository.findByName("ROLE_USER") == null) {
                roleRepository.save(new Role("ROLE_USER"));
            }

            // Seed Admin with an EMAIL instead of username
            if (userRepository.findByEmail("admin@hospital.com") == null) {
                Role adminRole = roleRepository.findByName("ROLE_ADMIN");
                User adminUser = new User("System Admin", "admin@hospital.com", passwordEncoder.encode("admin123"), List.of(adminRole));
                userRepository.save(adminUser);
            }
        };
    }
}