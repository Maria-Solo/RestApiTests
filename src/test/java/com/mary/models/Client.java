package com.mary.models;

import java.time.LocalDateTime;

public class Client {
    public Long id;
    public String name;
    public String email;
    public String phone;
    public String company;
    public LocalDateTime createdAt;

    public Client(String name, String email, String phone, String company, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.company = company;
        this.createdAt = createdAt;
    }

    public Client() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
