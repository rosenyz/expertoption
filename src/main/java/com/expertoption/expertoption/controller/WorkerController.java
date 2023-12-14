package com.expertoption.expertoption.controller;

import com.expertoption.expertoption.model.User;
import com.expertoption.expertoption.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/worker")
@RequiredArgsConstructor
public class WorkerController {
    private final UserService userService;

    @GetMapping("/my-users")
    public ResponseEntity<?> getWorkerUsers(Principal principal) {
        if (principal == null) { return new ResponseEntity<>(
                "Err",
                HttpStatus.INTERNAL_SERVER_ERROR); }
        User user = userService.getUserByPrincipal(principal);
        return ResponseEntity.ok(userService.findUsersByUsedRefToken(user.getGeneratedRefToken()));
    }
}
