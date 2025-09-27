package com.veri.taskmanager.controller;

import com.veri.taskmanager.dto.AuthRequest;
import com.veri.taskmanager.dto.AuthResponse;
import com.veri.taskmanager.entity.User;
import com.veri.taskmanager.repository.UserRepository;
import com.veri.taskmanager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthRequest request) {
        Map<String, String> response = new HashMap<>();

        // Check if username exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.warn("Attempt to register with existing username: {}", request.getUsername());
            response.put("error", "Username already exists");
            return ResponseEntity.badRequest().body(response);
        }

        // Check if password is empty or null
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            logger.warn("Attempt to register without password for username: {}", request.getUsername());
            response.put("error", "Password is required");
            return ResponseEntity.badRequest().body(response);
        }

        // Save user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        logger.info("New user registered: {}", request.getUsername());
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = jwtUtil.generateToken(request.getUsername());
            logger.info("User logged in successfully: {}", request.getUsername());

            // send token wrapped in JSON { "token": "..." }
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (Exception ex) {
            logger.error("Failed login attempt for username: {}", request.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }
}
