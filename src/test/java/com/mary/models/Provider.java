package com.mary.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class Provider {
    public Long id;
    public String name;
    public String email;
    public String phone;
    public ServiceType serviceType;
    public LocalDateTime createdAt;


    public enum ServiceType {
        CLOUD,
        SECURITY,
        ANALYTICS;
    }
}
