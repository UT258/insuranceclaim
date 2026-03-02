package com.claiminsight360.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class IdentityController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "identity-service");
    }

    @GetMapping("/users")
    public List<Map<String, Object>> listUsers() {
        return List.of(
            Map.of("userId", 1L, "name", "Alice Johnson", "role", "MANAGER", "email", "alice@example.com"),
            Map.of("userId", 2L, "name", "Bob Smith", "role", "ANALYST", "email", "bob@example.com"),
            Map.of("userId", 3L, "name", "Carol Davis", "role", "FRAUD", "email", "carol@example.com")
        );
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createUser(@RequestBody Map<String, Object> userDto) {
        return Map.of(
            "userId", 4L,
            "name", userDto.getOrDefault("name", "New User"),
            "role", userDto.getOrDefault("role", "ANALYST"),
            "email", userDto.getOrDefault("email", "user@example.com")
        );
    }

    @GetMapping("/auth/login")
    public Map<String, String> login(@RequestParam String email, @RequestParam String password) {
        return Map.of(
            "token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
            "userId", "1",
            "role", "MANAGER"
        );
    }
}
