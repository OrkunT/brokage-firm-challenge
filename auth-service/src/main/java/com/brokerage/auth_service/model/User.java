package com.brokerage.auth_service.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
public class User {

    @Id
    private String username;
    private String password;
    private String role;

    // Getters and Setters
}

