package com.brokerage.adminservice.controller;

import com.brokerage.adminservice.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/matchOrders")
    public ResponseEntity<Void> matchOrders() {
        adminService.matchOrders();
        return ResponseEntity.noContent().build();
    }
}
