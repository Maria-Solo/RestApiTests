package com.mary.models;

import java.time.Instant;
import java.time.LocalDateTime;

public class Provider {
    public Long id;
    public String name;
    public String email;
    public String phone;
    public ServiceType serviceType;
    public LocalDateTime createdAt;

    public Provider(Long id, String name, String email, String phone, ServiceType serviceType, LocalDateTime createdAt) {
        this.id = id;
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

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum ServiceType {
        CLOUD,
        SECURITY,
        ANALYTICS;
    }
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private ServiceType serviceType;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder serviceType(ServiceType serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Provider build() {
            Provider provider = new Provider();
            provider.id = this.id;
            provider.name = this.name;
            provider.email = this.email;
            provider.phone = this.phone;
            provider.serviceType = this.serviceType;
            provider.createdAt= this.createdAt;
            return provider;
        }
    }
}
