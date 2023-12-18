package com.expertoption.expertoption.controller;

import com.expertoption.expertoption.dto.request.UpdateRequest;
import com.expertoption.expertoption.dto.response.UserInfoFWResponse;
import com.expertoption.expertoption.model.User;
import com.expertoption.expertoption.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
@RequiredArgsConstructor
public class WorkerController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getWorkerUsers(Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User retrieval error"); }
        User user = userService.getUserByPrincipal(principal);
        List<UserInfoFWResponse> users = userService.findUsersByUsedRefToken(user.getGeneratedRefToken());
        if (users.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("У Вас нету привязанных пользователей!"); }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getWorkerUserById(@PathVariable(name = "id") Long id, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User retrieval error"); }
        if (userService.findById(id) == null) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь не найден!"); }
        if (userService.getUserByPrincipal(principal).getGeneratedRefToken().equals(userService.findById(id).getUsedRefToken())) {
            UserInfoFWResponse userInfoFWResponse = new UserInfoFWResponse(
                    userService.findById(id).getId(),
                    userService.findById(id).getUsername(),
                    userService.findById(id).getEmail(),
                    userService.findById(id).getActive(),
                    userService.findById(id).getVerification(),
                    userService.findById(id).getIsWithdrawAvailable(),
                    userService.findById(id).getUsedRefToken(),
                    userService.findById(id).getChance(),
                    userService.findById(id).getBalance(),
                    userService.findById(id).getRoles(),
                    userService.findById(id).getDateOfCreate()
            );
            return ResponseEntity.ok(userInfoFWResponse);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Пользователь с этим id должен быть привязан к Вам.");
    }

    @PostMapping("/users/update/{id}")
    public ResponseEntity<?> updateWorkerUserById(@PathVariable(name = "id") Long id, @RequestBody UpdateRequest updateRequest, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User retrieval error"); }
        if (userService.findById(id) == null) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь не найден!"); }
        if (!userService.getUserByPrincipal(principal).getGeneratedRefToken().equals(userService.findById(id).getUsedRefToken())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Пользователь с этим id должен быть привязан к Вам.");
        }

        User user = userService.findById(id);
        if (updateRequest.getActive() != null) { user.setActive(updateRequest.getActive()); }
        if (updateRequest.getVerification() != null) { user.setVerification(updateRequest.getVerification()); }
        if (updateRequest.getIsWithdrawAvailable() != null) { user.setIsWithdrawAvailable(updateRequest.getIsWithdrawAvailable()); }
        if (updateRequest.getChance() != null) { user.setChance(updateRequest.getChance()); }
        if (updateRequest.getBalance() != null) { user.setBalance(updateRequest.getBalance()); }
        userService.save(user);

        UserInfoFWResponse userInfoFWResponse = new UserInfoFWResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getActive(),
                user.getVerification(),
                user.getIsWithdrawAvailable(),
                user.getUsedRefToken(),
                user.getChance(),
                user.getBalance(),
                user.getRoles(),
                user.getDateOfCreate()
        );
        return ResponseEntity.ok(userInfoFWResponse);
    }
}
