package com.crypto.auth_service.controller;

import com.crypto.auth_service.dto.UserResponse;
import com.crypto.auth_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * Fetch all users.
     * Accessible only by ADMIN.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {

        return adminService.getAllUsers();
    }

    /**
     * Fetch user by ID.
     * Accessible only by ADMIN.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable Long id) {

        return adminService.getUserById(id);
    }

    /**
     * Delete user by ID.
     * Accessible only by ADMIN.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {

        adminService.deleteUser(id);

        return "User deleted successfully";
    }
}