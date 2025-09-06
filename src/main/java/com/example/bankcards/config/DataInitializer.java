package com.example.bankcards.config;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.RoleRepo;
import com.example.bankcards.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {
        Role adminRole = roleRepo.findByName("ADMIN")
                .orElseGet(() -> roleRepo.save(new Role("ADMIN")));

        Role userRole = roleRepo.findByName("USER")
                .orElseGet(() -> roleRepo.save(new Role("USER")));

        if (userRepo.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .enabled(true)
                    .roles(Set.of(adminRole, userRole))
                    .build();
            userRepo.save(admin);
        }
    }
}
