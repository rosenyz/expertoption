package com.expertoption.expertoption.controller;

import com.expertoption.expertoption.dto.response.UserInfoResponse;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> getInfoAboutUser(Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User retrieval error"); }
        User user = userService.getUserByPrincipal(principal);
        UserInfoResponse userInfoResponse = new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getVerification(),
                user.getBalance(),
                user.getBets(),
                user.getRoles(),
                user.getDateOfCreate()
        );
        return ResponseEntity.ok(userInfoResponse);
    }
}
