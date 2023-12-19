package com.expertoption.expertoption.controller;

import com.expertoption.expertoption.dto.request.UpdateRequest;
import com.expertoption.expertoption.dto.response.UserInfoFWResponse;
import com.expertoption.expertoption.model.User;
import com.expertoption.expertoption.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/users/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UpdateRequest updateRequest) {
        User user = userService.findById(id);
        if (user == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователя с таким id не существует!"); }
        if (updateRequest.getUsedRefToken() != null) { user.setUsedRefToken(updateRequest.getUsedRefToken()); }
        return userService.updateUserByEntity(updateRequest, user);
    }
}
