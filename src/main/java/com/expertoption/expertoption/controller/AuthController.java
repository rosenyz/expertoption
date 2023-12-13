package com.expertoption.expertoption.controller;

import com.expertoption.expertoption.component.JwtCore;
import com.expertoption.expertoption.dto.request.LoginRequest;
import com.expertoption.expertoption.dto.request.RegisterRequest;
import com.expertoption.expertoption.dto.response.LoginResponse;
import com.expertoption.expertoption.model.User;
import com.expertoption.expertoption.model.enums.Role;
import com.expertoption.expertoption.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtCore.generateToken(userService.loadUserByUsername(loginRequest.getUsername()));
        LoginResponse loginResponse = new LoginResponse(token);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, @RequestParam(name = "ref", required = false) String ref) {
        if (userService.existsByUsername(registerRequest.getUsername()) || userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким логином или почтой уже существует!");
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setActive(true);
        if (ref == null) { user.setUsedRefToken("0"); }
        user.setUsedRefToken(ref);
        user.getRoles().add(Role.ROLE_USER);
        userService.save(user);
        return ResponseEntity.ok("Пользователь успешно создан!");
    }
}
