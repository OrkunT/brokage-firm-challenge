package com.brokerage.adminservice.controller;

import com.brokerage.adminservice.service.AdminService;
import com.brokerage.common.domain.query.OrderState;
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
        adminService.fetchMatchOrders(OrderState.PENDING);
        return ResponseEntity.noContent().build();
    }
}
