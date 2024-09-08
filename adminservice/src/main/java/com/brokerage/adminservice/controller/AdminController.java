package com.brokerage.adminservice.controller;

import com.brokerage.adminservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/matchOrders")
    public ResponseEntity<Void> matchOrders() {
        adminService.matchOrders();
        return ResponseEntity.noContent().build();
    }
}