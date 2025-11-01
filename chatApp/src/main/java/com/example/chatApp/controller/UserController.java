package com.example.chatApp.controller;

import com.example.chatApp.Model.JwtResponse;
import com.example.chatApp.Model.User;
import com.example.chatApp.controller.UserDto; // ✅ Ensure you have this DTO in the correct package
import com.example.chatApp.security.JwtUtil;
import com.example.chatApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDTO) {
        userService.registerUser(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
        return ResponseEntity.ok("User registered successfully!");
    }

    // ✅ Login and return JWT token
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDTO) {
        try {
            User user = userService.findByUsername(userDTO.getUsername());

            if (userService.passwordMatches(userDTO.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // ✅ Verify token and fetch profile
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        try {
            User user = userService.findByUsername(username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
