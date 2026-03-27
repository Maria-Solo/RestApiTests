package com.mary.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class Task {
    public Long id;
    public String title;
    public String description;
    public String status;
    public Long clientId;
    public Long providerId;
    public Client client;
    public Provider provider;
}
