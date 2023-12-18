package com.expertoption.expertoption.controller;

import com.expertoption.expertoption.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        if (userService.getAllUsers().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ни одного пользователя не найдено!");
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/workers")
    public ResponseEntity<?> getAllWorkers() {
        if (userService.getAllUsersByRole("ROLE_WORKER").isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ни одного пользователя не найдено!");
        }
        return ResponseEntity.ok(userService.getAllUsersByRole("ROLE_WORKER"));
    }
}
