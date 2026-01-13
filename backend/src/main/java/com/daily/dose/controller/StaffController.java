package com.daily.dose.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.daily.dose.dto.StaffRequestDTO;
import com.daily.dose.dto.StaffResponseDTO;
import com.daily.dose.service.StaffService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")

@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }
    @PostMapping("/staff")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StaffResponseDTO> createStaff(
            @Valid @RequestBody StaffRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(staffService.createStaff(dto));
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<StaffResponseDTO> getStaff(@PathVariable long id) {
        return ResponseEntity.ok(staffService.getByID(id));
    }

    @GetMapping("/staff")
    public ResponseEntity<List<StaffResponseDTO>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllActive());
    }

    @PutMapping("/staff/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable long id) {
        staffService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
