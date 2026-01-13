package com.daily.dose.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.daily.dose.dto.LoginRequestDTO;
import com.daily.dose.security.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {

        try {
            Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                    )
                );

            String token =
                jwtService.generateToken(authentication.getName());

            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception ex) {
            return ResponseEntity
                .status(401)
                .body(Map.of("error", "Invalid email or password"));
        }
    }

}
