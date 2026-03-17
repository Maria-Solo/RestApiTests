package com.mary.models;

import java.time.LocalDateTime;

public class Provider {
    public Long id;
    public String name;
    public String email;
    public String phone;
    public String serviceType;
    public LocalDateTime createdAt;

    public Provider(String name, String email, String phone, String serviceType, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.serviceType = serviceType;
        this.createdAt = createdAt;
    }

    public Provider(){}

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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
